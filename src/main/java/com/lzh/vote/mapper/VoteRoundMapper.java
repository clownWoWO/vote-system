package com.lzh.vote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzh.vote.entity.DB.VoteRoundDB;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface VoteRoundMapper extends BaseMapper<VoteRoundDB> {

    Integer insertOne(@Param("voteRoundDB") VoteRoundDB voteRoundDB);

}
