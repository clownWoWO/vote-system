package com.lzh.vote.entity.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class CandidateReq {

    /**
     * 候选者名称
     */
    @NotBlank(message = "请输入候选者名称")
    private String name;
}
