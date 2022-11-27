package com.lzh.vote.service;

import com.lzh.vote.entity.CurrentUserReq;
import com.lzh.vote.entity.req.*;
import com.lzh.vote.entity.res.UserRes;
import com.lzh.vote.entity.res.UserVoteRes;
import com.lzh.vote.entity.res.VoteUserInfoRes;
import com.lzh.vote.exception.GenericException;
import com.lzh.vote.result.Result;

public interface IUserService {

    /**
     * 判断是否是管理员
     * @param id 用户id
     * @return UserRes
     */
    UserRes isAdmin (Integer id);

    /**
     * 用户登录
     * @param loginReq loginReq
     * @return
     * @throws GenericException
     */
    Result<UserRes> login(UserLoginReq loginReq) ;

    /**
     * 开始投票
     * @param voteStatusReq voteStatusReq
     * @param user
     * @return
     */
    Result startVote(VoteStatusReq voteStatusReq, CurrentUserReq user);

    /**
     * 结束投票
     * @param voteStatusReq voteStatusReq
     * @param user
     * @return UserVoteRes
     */
    Result<UserVoteRes> endVote(VoteStatusReq voteStatusReq, CurrentUserReq user);

    Result<VoteUserInfoRes> getVoteUserInfo(VoteUserpageReq voteUserpageReq, CurrentUserReq user);

    /**
     * 用户注册
     * @param registerUserReq registerUserReq
     * @return  Result
     */
    Result registerUser(RegisterUserReq registerUserReq);

    /**
     * 用户投票
     * @param req req
     * @param user
     * @return UserVoteRes
     */
    Result<UserVoteRes> vote(UserVoteReq req, CurrentUserReq user);

    /**
     * 获取选举情况
     * @param req
     * @param user
     * @return
     */
    Result<UserVoteRes> getCandidateInfo(CandidateInfoReq req, CurrentUserReq user);

    /**
     * 向投票用户发送最终结果
     * @param req
     * @param user
     * @return
     */
    Result sendMail(SendMailReq req, CurrentUserReq user);






}
