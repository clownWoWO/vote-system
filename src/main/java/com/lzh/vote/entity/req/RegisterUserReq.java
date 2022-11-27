package com.lzh.vote.entity.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class RegisterUserReq {
    @NotBlank(message = "请输入用户名")
    private String userName;
    @NotBlank(message = "请输入密码")
    private String password;
    @NotBlank(message = "身份证号")
    private String idCard;
    @NotBlank(message = "请输入邮箱号")
    private String email;
    @NotNull(message = "请输入用户标识")
    private Integer isAdmin;

}
