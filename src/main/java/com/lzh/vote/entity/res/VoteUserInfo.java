package com.lzh.vote.entity.res;

import lombok.Data;

import java.io.Serializable;

@Data
public class VoteUserInfo implements Serializable {
    /**
     * 投票者id
     */
    private Integer userId;
    /**
     * 投票者名称
     */
    private String userName;
    /**
     * 投票者身份证
     */
    private String idCard;
    /**
     * 投票者邮箱
     */
    private String email;
}
