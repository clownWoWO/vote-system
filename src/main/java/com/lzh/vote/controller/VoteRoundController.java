package com.lzh.vote.controller;

import com.lzh.vote.annotation.Authorization;
import com.lzh.vote.annotation.CurrentUser;
import com.lzh.vote.constant.VoteConstant;
import com.lzh.vote.entity.CurrentUserReq;
import com.lzh.vote.entity.req.VoteRoundAddReq;
import com.lzh.vote.entity.req.VoteRoundReq;
import com.lzh.vote.result.Result;
import com.lzh.vote.service.IVoteRoundService;
import com.lzh.vote.utils.ResultUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/voteRound")
public class VoteRoundController {
    private IVoteRoundService voteRoundService;

    /**
     * 添加选举场次
     * @param reqs reqs
     * @return
     */
    @PostMapping("/add")
    @Authorization(value = true)
    public Result addVoteRound(@Valid @RequestBody VoteRoundAddReq reqs, @CurrentUser CurrentUserReq user) {
        List<VoteRoundReq> voteRoundReqList = reqs.getVoteRoundReqList();
        if (voteRoundReqList.size() < VoteConstant.MINIMUM_CANDIDATE) {
            return ResultUtil.error(VoteConstant.MINIMUM_CANDIDATE_ERROR);
        }
        return voteRoundService.addVoteRound(reqs,user);
    }
}
