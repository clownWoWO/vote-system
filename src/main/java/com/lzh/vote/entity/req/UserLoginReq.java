package com.lzh.vote.entity.req;

import com.lzh.vote.utils.Base64Util;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserLoginReq implements Serializable {
    @NotBlank(message = "请输入用户名")
    private String userName;

    @NotBlank(message = "请输入密码")
    private String password;

    @NotNull(message = "请输入用户标识")
    private Integer isAdmin;

    public String encryptPassword(){

        try {
            this.password = Base64Util.encodeSafe(password);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return password;
    }
}
