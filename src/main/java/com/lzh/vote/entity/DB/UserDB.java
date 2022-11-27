package com.lzh.vote.entity.DB;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("t_user")
public class UserDB {
    @TableId(value = "id",type = IdType.AUTO)
    private Integer id;
    private String userName;
    private String password;
    private String email;
    private String idCard;
    private Integer status;
    private Integer isAdmin;

}
