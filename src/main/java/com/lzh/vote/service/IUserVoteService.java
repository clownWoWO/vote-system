package com.lzh.vote.service;

import com.lzh.vote.entity.CurrentUserReq;
import com.lzh.vote.entity.req.UserVoteReq;
import com.lzh.vote.entity.req.VoteUserpageReq;
import com.lzh.vote.entity.res.CandidateInfo;
import com.lzh.vote.entity.res.UserVoteRes;
import com.lzh.vote.entity.res.VoteUserInfo;
import com.lzh.vote.result.Pageable;
import com.lzh.vote.result.Result;

import java.util.List;

public interface IUserVoteService {
    /**
     * 投票
     * @param req req
     * @param user
     * @return
     */
    Result<UserVoteRes> vote(UserVoteReq req, CurrentUserReq user);

    List<CandidateInfo> getCandidateInfos(Integer roundId);

    Pageable<VoteUserInfo> getVoteUserPage(VoteUserpageReq voteUserpageReq);

    List<VoteUserInfo> selectUserInfoByRoundId(Integer roundId);


}
