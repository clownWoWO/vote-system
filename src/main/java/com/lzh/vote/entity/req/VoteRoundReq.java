package com.lzh.vote.entity.req;

import lombok.Data;

import javax.validation.constraints.Null;

@Data
public class VoteRoundReq {

    /**
     * 候选人id
     */
    @Null(message = "请输入候选者id")
    private Integer candidateId;

}
