package com.lzh.vote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzh.vote.constant.VoteConstant;
import com.lzh.vote.entity.CurrentUserReq;
import com.lzh.vote.entity.DB.*;
import com.lzh.vote.entity.req.*;
import com.lzh.vote.entity.res.*;
import com.lzh.vote.exception.GenericException;
import com.lzh.vote.mapper.CandidateMapper;
import com.lzh.vote.mapper.UserMapper;
import com.lzh.vote.mapper.VoteResultMapper;
import com.lzh.vote.mapper.VoteRoundMapper;
import com.lzh.vote.result.Pageable;
import com.lzh.vote.result.Result;
import com.lzh.vote.result.ResultEnum;
import com.lzh.vote.service.IMailService;
import com.lzh.vote.service.IUserService;
import com.lzh.vote.service.IUserVoteService;
import com.lzh.vote.service.TokenManager;
import com.lzh.vote.utils.Base64Util;
import com.lzh.vote.utils.RegexUtil;
import com.lzh.vote.utils.ResultUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements IUserService {

    private UserMapper userMapper;
    private VoteRoundMapper voteRoundMapper;
    private IUserVoteService userVoteService;
    private CandidateMapper candidateMapper;
    private VoteResultMapper voteResultMapper;
    private IMailService mailService;
    private TokenManager tokenManager;

    @Override
    public UserRes isAdmin(Integer id) {
        UserRes userRes = new UserRes();
        UserDB userDB = userMapper.selectById(id);
        if (ObjectUtils.isEmpty(userDB)) {
            return userRes;
        }
        if (VoteConstant.IS_ADMIN.equals(userDB.getIsAdmin()) && VoteConstant.IS_USING.equals(userDB.getStatus())) {
            BeanUtils.copyProperties(userDB, userRes);
        }
        return userRes;
    }

    @Override
    public Result<UserRes> login(UserLoginReq loginReq) {

        String encryptPassword = loginReq.encryptPassword();
        if (ObjectUtils.isEmpty(encryptPassword)) {
            return ResultUtil.error(ResultEnum.ERROR_DESC_PASSWORD);
        }

        LambdaQueryWrapper<UserDB> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserDB::getUserName, loginReq.getUserName());
        queryWrapper.eq(UserDB::getPassword, loginReq.getPassword());
        queryWrapper.eq(UserDB::getIsAdmin, loginReq.getIsAdmin());
        queryWrapper.eq(UserDB::getStatus, 0);
        UserDB userDB = userMapper.selectOne(queryWrapper);

        if (ObjectUtils.isEmpty(userDB)) {
            return ResultUtil.error(ResultEnum.LOGIN_ERROR);
        }

        UserRes userRes = new UserRes();
        BeanUtils.copyProperties(userDB, userRes);
        //生成一个token，保存用户登录状态
        tokenManager.createToken(userRes.getId().toString());
        return ResultUtil.success(userRes);
    }

    @Override
    public Result startVote(VoteStatusReq voteStatusReq, CurrentUserReq user) {

        LambdaUpdateWrapper<VoteRoundDB> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(VoteRoundDB::getId, voteStatusReq.getRoundId());
        VoteRoundDB update = new VoteRoundDB();
        Date date = new Date();
        update.setUpdateUser(user.getUserName());
        update.setUpdateTime(date);
        update.setStatus(VoteConstant.START_VOTE_STATUS);
        Integer count = voteRoundMapper.update(update, updateWrapper);
        if (count > 0) {
            return ResultUtil.success();
        }
        return ResultUtil.error(VoteConstant.UPDATE_VOTE_STATUS_ERROR);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<UserVoteRes> endVote(VoteStatusReq voteStatusReq, CurrentUserReq user) {

        LambdaUpdateWrapper<VoteRoundDB> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(VoteRoundDB::getId, voteStatusReq.getRoundId());
        VoteRoundDB update = new VoteRoundDB();
        Date date = new Date();
        update.setUpdateUser(user.getUserName());
        update.setUpdateTime(date);
        update.setStatus(VoteConstant.END_VOTE_STATUS);
        Integer count = voteRoundMapper.update(update, updateWrapper);
        if (count > 0) {
            CandidateInfoReq candidateInfoReq = new CandidateInfoReq();
            candidateInfoReq.setRoundId(voteStatusReq.getRoundId());

            Result<UserVoteRes> candidateInfo = this.getCandidateInfo(candidateInfoReq,user);

            //向结果表中插入数据
            this.insertToResult(candidateInfo.getData());

            return candidateInfo;
        }
        return ResultUtil.error(VoteConstant.UPDATE_VOTE_STATUS_ERROR);
    }

    private void insertToResult(UserVoteRes data) {
        Integer roundId = data.getRoundId();
        List<CandidateInfo> candidateInfoList = data.getCandidateInfoList();
        for (CandidateInfo candidateInfo : candidateInfoList) {
            VoteResultDB voteResultDB = new VoteResultDB();
            voteResultDB.setCandidateId(candidateInfo.getCandidateId());
            voteResultDB.setRoundId(roundId);
            voteResultDB.setVoteCount(candidateInfo.getVoteCount());

            VoteResultDB one = voteResultMapper.selectByRoundIdAndCandidateId(roundId, candidateInfo.getCandidateId());
            if (!ObjectUtils.isEmpty(one)) {
                LambdaUpdateWrapper<VoteResultDB> updateWrapper = Wrappers.lambdaUpdate();
                updateWrapper.eq(VoteResultDB::getRoundId, roundId);
                updateWrapper.eq(VoteResultDB::getCandidateId, candidateInfo.getCandidateId());
                voteResultMapper.update(voteResultDB, updateWrapper);
            } else {
                voteResultMapper.insert(voteResultDB);
            }
        }
    }

    @Override
    public Result<VoteUserInfoRes> getVoteUserInfo(VoteUserpageReq voteUserpageReq, CurrentUserReq user) {

        VoteRoundDB voteRoundDB = voteRoundMapper.selectById(voteUserpageReq.getRoundId());
        CandidateDB candidateDB = candidateMapper.selectById(voteUserpageReq.getCandidateId());

        Pageable<VoteUserInfo> voteUserPage = userVoteService.getVoteUserPage(voteUserpageReq);

        VoteUserInfoRes voteUserInfoRes = new VoteUserInfoRes();
        voteUserInfoRes.setRoundId(voteRoundDB.getId());
        voteUserInfoRes.setRoundName(voteRoundDB.getRoundName());
        voteUserInfoRes.setCandidateId(candidateDB.getId());
        voteUserInfoRes.setCandidateName(candidateDB.getName());
        voteUserInfoRes.setPageable(voteUserPage);

        return ResultUtil.success(voteUserInfoRes);
    }

    @SneakyThrows
    @Override
    public Result registerUser(RegisterUserReq registerUserReq) {
        if (!RegexUtil.verifyIdCard(registerUserReq.getIdCard())) {
            throw new GenericException(VoteConstant.ERROR_CODE_ID_CARD, VoteConstant.ERROR_DESC_ID_CARD);
        }
        if (!RegexUtil.verifyEmail(registerUserReq.getEmail())) {
            throw new GenericException(VoteConstant.ERROR_CODE_EMAIL, VoteConstant.ERROR_DESC_EMAIL);
        }
        String encode = Base64Util.encode(registerUserReq.getPassword());
        UserDB userDB = new UserDB();
        userDB.setUserName(registerUserReq.getUserName());
        userDB.setPassword(encode);
        userDB.setIsAdmin(registerUserReq.getIsAdmin());
        userDB.setEmail(registerUserReq.getEmail());
        userDB.setIdCard(registerUserReq.getIdCard());

        userMapper.insert(userDB);

        return ResultUtil.success();
    }

    @Override
    public Result<UserVoteRes> vote(UserVoteReq req, CurrentUserReq user) {

        return userVoteService.vote(req,user);
    }

    @Override
    public Result<UserVoteRes> getCandidateInfo(CandidateInfoReq req, CurrentUserReq user) {

        VoteRoundDB voteRoundDB = voteRoundMapper.selectById(req.getRoundId());
        List<CandidateInfo> candidateInfos = userVoteService.getCandidateInfos(req.getRoundId());

        UserVoteRes userVoteRes = new UserVoteRes();
        userVoteRes.setRoundStatus(voteRoundDB.getStatus());
        userVoteRes.setRoundName(voteRoundDB.getRoundName());
        userVoteRes.setRoundId(voteRoundDB.getId());
        userVoteRes.setCandidateInfoList(candidateInfos);

        return ResultUtil.success(userVoteRes);
    }

    @Override
    public Result sendMail(SendMailReq req, CurrentUserReq user) {

        List<VoteUserInfo> voteUserInfos = userVoteService.selectUserInfoByRoundId(req.getRoundId());
        if (ObjectUtils.isEmpty(voteUserInfos)) {
            log.info("roundId为：{},无投票用户", req.getRoundId());
            return ResultUtil.success();
        }

        List<VoteResultRes> voteResultResList = voteResultMapper.selectByRoundId(req.getRoundId());
        if (ObjectUtils.isEmpty(voteResultResList)) {
            log.info("roundId为：{},未查到选举结果，请确认选举是否结束", req.getRoundId());
            return ResultUtil.success();
        }
        String content = String.format("%s的选举结果如下：", voteResultResList.get(0).getRoundName()) + "\n";
        for (VoteResultRes voteResultRes : voteResultResList) {
            String candidateName = voteResultRes.getCandidateName();
            Integer voteCount = voteResultRes.getVoteCount();
            content = content + String.format("%s得票数为：%s", candidateName, voteCount) + "\n";
        }
        List<String> tos = voteUserInfos.stream().map(VoteUserInfo::getEmail).distinct().collect(Collectors.toList());
        mailService.batchSendSimpleMail(tos, VoteConstant.EMAIL_SUBJECT, content);
        return ResultUtil.success();
    }
}
