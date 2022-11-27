package com.lzh.vote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzh.vote.entity.DB.UserVoteDB;
import com.lzh.vote.entity.res.CandidateInfo;
import com.lzh.vote.entity.res.VoteUserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserVoteMapper extends BaseMapper<UserVoteDB> {

    List<CandidateInfo> getCandidateInfos(Integer roundId);

    int getTotalCount(@Param("roundId") Integer roundId, @Param("candidateId") Integer candidateId);

    List<VoteUserInfo> getVoteUserPage(@Param("roundId") Integer roundId, @Param("candidateId") Integer candidateId,
                                       @Param("offset") int offset, @Param("pageSize") int pageSize);

    List<VoteUserInfo> selectUserVotesByRoundId(Integer roundId);
}
