package com.lzh.vote.constant;

public class VoteConstant {

    /**
     * 存储当前登录用户id的字段名
     */
    public static final String CURRENT_USER_ID = "CURRENT_USER_ID";

    /**
     * token有效期（小时）
     */
    public static final int TOKEN_EXPIRES_HOUR = 72;


    public static final String USERID_KEY = "user_id:";

    /**
     * 存放Authorization的header字段
     */
    public static final String TOKEN = "token";

    public static final String ERROR_CODE_PASSWORD = "0001";

    public static final String ERROR_DESC_PASSWORD = "密码加密失败";

    public static final Integer IS_ADMIN = 1;

    public static final Integer IS_USING = 0;

    public static final String ADD_CANDIDATE_ERROR = "添加候选人失败";

    public static final String IS_NOT_ADMIN_ERROR = "您不是管理员无权操作";

    public static final String UPDATE_VOTE_STATUS_ERROR = "选举状态更新失败";
    /**
     * 最小候选人数
     */
    public static final int MINIMUM_CANDIDATE = 2;
    public static final String MINIMUM_CANDIDATE_ERROR = "至少要有两个候选人";
    /**
     * 选举开始
     */
    public static final Integer START_VOTE_STATUS = 1;
    /**
     * 选举结束
     */
    public static final Integer END_VOTE_STATUS = 2;
    public static final String ERROR_CODE_ID_CARD = "00002";

    public static final String ERROR_DESC_ID_CARD = "身份证号不合法";
    public static final String ERROR_CODE_EMAIL = "00003";
    public static final String ERROR_DESC_EMAIL = "邮箱不合法";
    public static final String ERROR_CODE_VOTE = "00004";

    public static final String ERROR_DESC_VOTE = "选举已结束，或未开始";
    public static final String EMAIL_SUBJECT = "投票选举结果";
}
