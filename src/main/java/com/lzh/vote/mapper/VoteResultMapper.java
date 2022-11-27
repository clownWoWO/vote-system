package com.lzh.vote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzh.vote.entity.DB.CandidateDB;
import com.lzh.vote.entity.DB.VoteResultDB;
import com.lzh.vote.entity.res.VoteResultRes;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface VoteResultMapper extends BaseMapper<VoteResultDB> {

    @Select("select id,round_id,candidate_id from t_vote_result where round_id=#{roundId} and candidate_id=#{candidateId}")
    VoteResultDB selectByRoundIdAndCandidateId(@Param("roundId") Integer roundId,@Param("candidateId") Integer candidateId);

    @Select("SELECT\n" +
            "\ttvr.id,\n" +
            "\ttvr.vote_count,\n" +
            "\ttvr.round_id,\n" +
            "\ttvro.round_name,\n" +
            "\ttvr.candidate_id,\n" +
            "\ttc.`name` candidate_name \n" +
            "FROM\n" +
            "\tt_vote_result tvr\n" +
            "\tLEFT JOIN t_vote_round tvro ON tvro.id = tvr.round_id\n" +
            "\tLEFT JOIN t_candidate tc ON tc.id = tvr.candidate_id \n" +
            "WHERE\n" +
            "\ttvr.round_id = #{roundId} order by tvr.vote_count desc")
    List<VoteResultRes> selectByRoundId(Integer roundId);
}
