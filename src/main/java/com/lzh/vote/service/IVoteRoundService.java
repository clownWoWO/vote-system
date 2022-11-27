package com.lzh.vote.service;

import com.lzh.vote.entity.CurrentUserReq;
import com.lzh.vote.entity.req.VoteRoundAddReq;
import com.lzh.vote.result.Result;

public interface IVoteRoundService {
    /**
     * 添加一场选举
     * @param reqs
     * @param user
     * @return
     */
    Result addVoteRound(VoteRoundAddReq reqs, CurrentUserReq user);
}
