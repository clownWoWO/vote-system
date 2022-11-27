package com.lzh.vote.entity.DB;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_vote_round")
public class VoteRoundDB {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String roundName;
    private Integer status;
    private String createUser;
    private Date createTime;
    private String updateUser;
    private Date updateTime;
}
