package com.lzh.vote.entity.DB;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("t_round_candidate_conn")
public class RoundCandidateConnDB {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private Integer candidateId;
    private Integer roundId;

    private Integer deleted;
    private String createUser;
    private Date createTime;
}
