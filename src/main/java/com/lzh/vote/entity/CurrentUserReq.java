package com.lzh.vote.entity;

import lombok.Data;

import java.io.Serializable;
@Data
public class CurrentUserReq implements Serializable {
    private Integer id;
    private String userName;
}
