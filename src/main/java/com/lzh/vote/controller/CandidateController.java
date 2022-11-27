package com.lzh.vote.controller;

import com.lzh.vote.annotation.Authorization;
import com.lzh.vote.annotation.CurrentUser;
import com.lzh.vote.constant.VoteConstant;
import com.lzh.vote.entity.CurrentUserReq;
import com.lzh.vote.entity.req.CandidateReq;
import com.lzh.vote.result.Result;
import com.lzh.vote.service.ICandidateService;
import com.lzh.vote.utils.ResultUtil;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/candidate")
@AllArgsConstructor
public class CandidateController {
    private ICandidateService candidateService;

    /**
     * 新增候选人
     * @param req req
     * @return Result
     */
    @PostMapping("/add")
    @Authorization(value = true)
    public Result add(@Valid @RequestBody CandidateReq req, @CurrentUser CurrentUserReq user) {
        Integer add = candidateService.addCandidate(req,user);
        if (add <= 0) {
            return ResultUtil.error(VoteConstant.ADD_CANDIDATE_ERROR);
        }
        return ResultUtil.success();
    }
}
