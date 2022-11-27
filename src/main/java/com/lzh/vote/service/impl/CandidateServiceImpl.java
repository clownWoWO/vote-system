package com.lzh.vote.service.impl;

import com.lzh.vote.entity.CurrentUserReq;
import com.lzh.vote.entity.DB.CandidateDB;
import com.lzh.vote.entity.req.CandidateReq;
import com.lzh.vote.mapper.CandidateMapper;
import com.lzh.vote.service.ICandidateService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@AllArgsConstructor
public class CandidateServiceImpl implements ICandidateService {
    private CandidateMapper candidateMapper;

    @Override
    public Integer addCandidate(CandidateReq candidateReq, CurrentUserReq user) {

        Date date = new Date();
        CandidateDB candidateDB = new CandidateDB();
        candidateDB.setName(candidateReq.getName());
        candidateDB.setCreateUser(user.getUserName());
        candidateDB.setCreateTime(date);

        return candidateMapper.insert(candidateDB);
    }

}
