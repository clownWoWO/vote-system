package com.lzh.vote.entity.res;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class UserVoteRes implements Serializable {
    private Integer roundId;
    private String roundName;
    private Integer roundStatus;
    private List<CandidateInfo> candidateInfoList;

}
