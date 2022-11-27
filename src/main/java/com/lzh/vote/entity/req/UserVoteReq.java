package com.lzh.vote.entity.req;

import lombok.Data;

@Data
public class UserVoteReq {

    private Integer roundId;
    private Integer candidateId;
}
