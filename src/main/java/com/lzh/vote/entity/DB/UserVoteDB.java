package com.lzh.vote.entity.DB;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_user_vote")
public class UserVoteDB {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer roundId;
    private Integer userId;
    private Integer candidateId;
    private Date createTime;
    private Date updateTime;
    private Integer deleted;
}
