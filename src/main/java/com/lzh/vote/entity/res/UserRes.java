package com.lzh.vote.entity.res;

import lombok.Data;

@Data
public class UserRes {
    private Integer id;
    private String userName;
    private String email;
    private Integer status;
    private Integer isAdmin;
}
