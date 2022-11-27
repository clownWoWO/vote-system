package com.lzh.vote.controller;

import com.lzh.vote.annotation.Authorization;
import com.lzh.vote.annotation.CurrentUser;
import com.lzh.vote.entity.CurrentUserReq;
import com.lzh.vote.entity.req.*;
import com.lzh.vote.entity.res.UserRes;
import com.lzh.vote.entity.res.UserVoteRes;
import com.lzh.vote.entity.res.VoteUserInfoRes;
import com.lzh.vote.exception.GenericException;
import com.lzh.vote.result.Result;
import com.lzh.vote.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {

    private IUserService userService;

    /**
     * 用户登录
     * @param req req
     * @return UserLoginReq
     * @throws GenericException
     */
    @PostMapping("/login")
    public Result<UserRes> login(@Validated @RequestBody UserLoginReq req)  {
        return userService.login(req);
    }

    /**
     * 开启投票
     * @param req
     * @return
     */
    @GetMapping("/start/vote")
    @Authorization(value = true)
    public Result startVote(VoteStatusReq req, @CurrentUser CurrentUserReq user){
        return userService.startVote(req,user);
    }

    /**
     * 关闭投票
     * @param req VoteStatusReq
     * @return
     */
    @GetMapping("/end/vote")
    @Authorization(value = true)
    public Result<UserVoteRes> endVote(VoteStatusReq req, @CurrentUser CurrentUserReq user){
        return userService.endVote(req,user);
    }

    /**
     * 获取投票用户列表
     * @param req req
     * @return VoteUserInfoRes
     */
    @GetMapping("/user/vote")
    @Authorization(value = true)
    public Result<VoteUserInfoRes> getVoteUserInfo(VoteUserpageReq req, @CurrentUser CurrentUserReq user){
        return userService.getVoteUserInfo(req,user);
    }

    /**
     * 用户注册
     * @param req
     * @return
     */
    @PostMapping("/register")
    public Result registerUser(@Validated @RequestBody RegisterUserReq req){
        return userService.registerUser(req);
    }

    /**
     * 用户投票
     * @param userVoteReq userVoteReq
     * @return Result
     */
    @GetMapping("/vote")
    @Authorization(value = false)
    public Result<UserVoteRes> vote(UserVoteReq userVoteReq, @CurrentUser CurrentUserReq user){
        return userService.vote(userVoteReq,user);
    }

    /**
     * 候选人实时得票状态
     * @param req
     * @return
     */
    @GetMapping("/candidateInfos")
    @Authorization(value = false)
    public Result<UserVoteRes> getCandidateInfo(CandidateInfoReq req,@CurrentUser CurrentUserReq user){
        return userService.getCandidateInfo(req, user);
    }

    /**
     * 发送邮件
     * @param req
     * @return
     */
    @Async
    @GetMapping("/send/email")
    @Authorization(value = true)
    public Result sendEmail(SendMailReq req,@CurrentUser CurrentUserReq user){
        return userService.sendMail(req,user);
    }
}
