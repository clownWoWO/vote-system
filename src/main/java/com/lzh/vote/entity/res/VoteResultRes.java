package com.lzh.vote.entity.res;

import lombok.Data;

@Data
public class VoteResultRes {
    private Integer id;
    private Integer roundId;
    private String roundName;
    private Integer candidateId;
    private String candidateName;
    private Integer voteCount;

}
