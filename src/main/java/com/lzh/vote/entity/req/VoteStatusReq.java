package com.lzh.vote.entity.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VoteStatusReq {

    /**
     * 选举场次id
     */
    @NotNull(message = "请输入选举场次")
    private Integer roundId;
}
