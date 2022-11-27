package com.lzh.vote.entity.DB;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_vote_result")
public class VoteResultDB {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer candidateId;
    private Integer roundId;
    private Integer voteCount;
}
