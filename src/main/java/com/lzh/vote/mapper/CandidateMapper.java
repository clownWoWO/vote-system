package com.lzh.vote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzh.vote.entity.DB.CandidateDB;
import com.lzh.vote.entity.DB.UserDB;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CandidateMapper extends BaseMapper<CandidateDB> {

}
