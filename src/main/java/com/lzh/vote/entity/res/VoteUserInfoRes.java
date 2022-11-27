package com.lzh.vote.entity.res;

import com.lzh.vote.result.Pageable;
import lombok.Data;

import java.io.Serializable;
@Data
public class VoteUserInfoRes implements Serializable {
    private Integer roundId;
    private String roundName;
    private Integer candidateId;
    private String candidateName;
    private Pageable<VoteUserInfo> pageable;
}
