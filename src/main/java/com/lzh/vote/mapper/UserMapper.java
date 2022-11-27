package com.lzh.vote.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lzh.vote.entity.DB.UserDB;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<UserDB> {

}
