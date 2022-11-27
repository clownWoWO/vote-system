package com.lzh.vote.entity.req;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class VoteUserpageReq {

    /**
     * 选举场次Id
     */
    @NotNull(message = "请输入选举场次")
    private Integer roundId;
    /**
     * 候选人Id
     */
    @NotNull(message = "请选择候选者id")
    private Integer candidateId;
    /**
     * 当前页码
     */
    @NotNull(message = "请输入当前页码")
    private Integer currentPage;
    /**
     * 页容量
     */
    @NotNull(message = "请输入页容量")
    private Integer pageSize;
}
