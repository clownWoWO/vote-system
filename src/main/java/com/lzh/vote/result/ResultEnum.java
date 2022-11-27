package com.lzh.vote.result;

public enum ResultEnum {
    UNKNOWN_ERROR(-1, "未知错误"),
    SUCCESS(200, "成功"),
    SERVER_INTERNAL_ERROR(500, "服务器内部错误"),
    RESOURCE_NOT_FOUND(404, "资源未找到"),
    PARAMETER_NOT_VALID(400, "参数不合法"),
    LOGIN_ERROR(-2,"用户名或密码不正确"),
    VOTE_END_ERROR(-3,"选举已结束，或未开始"),
    VOTED_ERROR(-4,"请勿重复投票"),
    ERROR_DESC_PASSWORD(-5,"密码加密失败")
    ;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}