package com.lzh.vote.entity.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class CandidateInfo implements Serializable {
    private Integer candidateId;
    private String candidateName;
    private Integer voteCount;

}
