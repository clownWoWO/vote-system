package com.lzh.vote.entity.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Null;
import java.util.List;

@Data
public class VoteRoundAddReq {
    /**
     * 场次名称
     */
    @NotBlank(message = "请输入候选场次名称")
    private String roundName;

    @NotEmpty(message = "至少添加两位候选人")
    private List<VoteRoundReq> voteRoundReqList;
}
