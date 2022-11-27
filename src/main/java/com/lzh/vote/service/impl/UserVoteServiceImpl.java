package com.lzh.vote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzh.vote.constant.VoteConstant;
import com.lzh.vote.entity.CurrentUserReq;
import com.lzh.vote.entity.DB.UserVoteDB;
import com.lzh.vote.entity.DB.VoteRoundDB;
import com.lzh.vote.entity.req.UserVoteReq;
import com.lzh.vote.entity.req.VoteUserpageReq;
import com.lzh.vote.entity.res.CandidateInfo;
import com.lzh.vote.entity.res.UserVoteRes;
import com.lzh.vote.entity.res.VoteUserInfo;
import com.lzh.vote.mapper.UserVoteMapper;
import com.lzh.vote.mapper.VoteRoundMapper;
import com.lzh.vote.result.Pageable;
import com.lzh.vote.result.Result;
import com.lzh.vote.result.ResultEnum;
import com.lzh.vote.service.IUserVoteService;
import com.lzh.vote.utils.ResultUtil;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class UserVoteServiceImpl implements IUserVoteService {

    private UserVoteMapper userVoteMapper;
    private VoteRoundMapper voteRoundMapper;

    @SneakyThrows
    @Override
    public Result<UserVoteRes> vote(UserVoteReq req, CurrentUserReq user) {

        //获取选举场次信息
        VoteRoundDB voteRoundDB = voteRoundMapper.selectById(req.getRoundId());
        if (ObjectUtils.isEmpty(voteRoundDB) || !VoteConstant.START_VOTE_STATUS.equals(voteRoundDB.getStatus())) {
            return ResultUtil.error(ResultEnum.VOTE_END_ERROR);
        }

        //不能重复投票
        LambdaQueryWrapper<UserVoteDB> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(UserVoteDB::getUserId, user.getId())
                .eq(UserVoteDB::getRoundId, req.getRoundId())
                .eq(UserVoteDB::getCandidateId, req.getCandidateId())
                .eq(UserVoteDB::getDeleted, 0);
        UserVoteDB selectOne = userVoteMapper.selectOne(queryWrapper);
        if (!ObjectUtils.isEmpty(selectOne)) {
            return ResultUtil.error(ResultEnum.VOTED_ERROR);
        }

        //用户投票
        UserVoteDB userVoteDB = new UserVoteDB();
        Date date = new Date();
        BeanUtils.copyProperties(req, userVoteDB);
        userVoteDB.setCreateTime(date);
        userVoteMapper.insert(userVoteDB);

        //获取选举投票信息
        List<CandidateInfo> candidateInfos = this.getCandidateInfos(req.getRoundId());

        UserVoteRes userVoteRes = new UserVoteRes();
        userVoteRes.setRoundId(voteRoundDB.getId());
        userVoteRes.setRoundName(voteRoundDB.getRoundName());
        userVoteRes.setRoundStatus(voteRoundDB.getStatus());
        userVoteRes.setCandidateInfoList(candidateInfos);

        return ResultUtil.success(userVoteRes);
    }

    /**
     * 获取选举投票信息
     *
     * @param roundId roundId
     * @return
     */
    @Override
    public List<CandidateInfo> getCandidateInfos(Integer roundId) {
        return userVoteMapper.getCandidateInfos(roundId);
    }

    @Override
    public Pageable<VoteUserInfo> getVoteUserPage(VoteUserpageReq req) {

        int totalCount = userVoteMapper.getTotalCount(req.getRoundId(), req.getCandidateId());
        Pageable<VoteUserInfo> pageable = new Pageable<>(req.getCurrentPage(), req.getPageSize(), totalCount);
        List<VoteUserInfo> list = userVoteMapper.getVoteUserPage(req.getRoundId(), req.getCandidateId(), pageable.getOffset(), pageable.getPageSize());
        pageable.setList(list);
        return pageable;
    }

    @Override
    public List<VoteUserInfo> selectUserInfoByRoundId(Integer roundId) {

        return userVoteMapper.selectUserVotesByRoundId(roundId);
    }

}
