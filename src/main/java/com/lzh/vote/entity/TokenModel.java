package com.lzh.vote.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Token的Model类，可以增加字段提高安全性，例如时间戳、url签名
 */
@Data
@AllArgsConstructor
public class TokenModel {

    //用户id
    private String userId;

    //随机生成的uuid
    private String token;
}
