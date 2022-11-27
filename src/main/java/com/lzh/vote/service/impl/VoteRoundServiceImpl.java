package com.lzh.vote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.lzh.vote.constant.VoteConstant;
import com.lzh.vote.entity.CurrentUserReq;
import com.lzh.vote.entity.DB.RoundCandidateConnDB;
import com.lzh.vote.entity.DB.VoteRoundDB;
import com.lzh.vote.entity.req.VoteRoundAddReq;
import com.lzh.vote.entity.req.VoteRoundReq;
import com.lzh.vote.entity.res.UserRes;
import com.lzh.vote.mapper.RoundCandidateConnMapper;
import com.lzh.vote.mapper.VoteRoundMapper;
import com.lzh.vote.result.Result;
import com.lzh.vote.service.IUserService;
import com.lzh.vote.service.IVoteRoundService;
import com.lzh.vote.utils.ResultUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@AllArgsConstructor
@Slf4j
public class VoteRoundServiceImpl implements IVoteRoundService {
    private IUserService userService;

    private VoteRoundMapper voteRoundMapper;

    private RoundCandidateConnMapper roundCandidateConnMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result addVoteRound(VoteRoundAddReq reqs, CurrentUserReq user) {
        Date date = new Date();
        //插入选举场次（默认选举未开始）
        VoteRoundDB voteRoundDB = new VoteRoundDB();
        voteRoundDB.setRoundName(reqs.getRoundName());
        voteRoundDB.setCreateUser(user.getUserName());
        voteRoundDB.setCreateTime(date);
        voteRoundMapper.insertOne(voteRoundDB);
        log.info("voteRoundId 是{}", voteRoundDB.getId());

        //插入选举场次和候选人关联表
        for (VoteRoundReq req : reqs.getVoteRoundReqList()) {
            RoundCandidateConnDB roundCandidateConnDB = new RoundCandidateConnDB();
            roundCandidateConnDB.setCandidateId(req.getCandidateId());
            roundCandidateConnDB.setRoundId(voteRoundDB.getId());
            roundCandidateConnDB.setCreateTime(date);
            roundCandidateConnDB.setCreateUser(user.getUserName());

            //防止重复添加
            if (!ObjectUtils.isEmpty(this.getConnDB(roundCandidateConnDB))){
                roundCandidateConnMapper.insert(roundCandidateConnDB);
            }
        }
        return ResultUtil.success();
    }

    private RoundCandidateConnDB getConnDB(RoundCandidateConnDB roundCandidateConnDB) {
        LambdaQueryWrapper<RoundCandidateConnDB> queryWrapper = Wrappers.lambdaQuery();
        queryWrapper.eq(RoundCandidateConnDB::getRoundId, roundCandidateConnDB.getRoundId());
        queryWrapper.eq(RoundCandidateConnDB::getCandidateId, roundCandidateConnDB.getCandidateId());
        return roundCandidateConnMapper.selectOne(queryWrapper);
    }
}
