package com.lzh.vote.service;

import com.lzh.vote.entity.CurrentUserReq;
import com.lzh.vote.entity.req.CandidateReq;

public interface ICandidateService {

    Integer addCandidate(CandidateReq candidateReq, CurrentUserReq user);


}
