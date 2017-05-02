package com.hp.blog.common.Enum;

public enum ErrorCodeEnum {

    /************* 系统公用 *******************************/
    SERVER_SYSERR(1001, "系统内部错误"),
    SYSTEM_INVALID_PARAM(1002, "参数非法"),
    SYSTEM_ITEM_NOT_EXISTED(1003, "请求项不存在"),
    SYSTEM_NO_PERMISSIONS(1004,"权限拒绝"),
    SYSTEM_MOBILE_INVALID(1005,"手机号非法"),
    SYSTEM_ID_CARD_INVALID(1006,"身份证号非法"),


    /***************登录错误码**************/
    LOGIN_VERCODE_ERROR(1101,"验证码错误"),
    LOGIN_USER_NO_EXIST(1102,"用户不存在"),
    LOGIN_PASSWORD_ERROR(1103,"密码错误"),
    LOGIN_WX_NOT_INIT(1104, "微信账户未初始化"),
    LOGIN_TOO_FREQUENT(1105,"登录过于频繁,请10分钟后再登录"),
    LOGIN_PSWD_UNUSUAL(1106,"密码异常"),
    LOGIN_BAN_USER(1107,"账号被禁用"),


    /**************用户错误码****************/
    USER_SESSION_EXPIRE(1201,"会话过期"),
    USER_GEN_ID_FAIL(1202, "生成用户id异常"),
    USER_MOBILE_NO_ERROR(1203, "手机号码错误"),
    USER_OLDPASSWORD_ERROR(1204,"旧密码错误"),



    BLOG_TYPE_ERROR(2000,"请选择类型")
    ;



    
    private int code;

    private String remark;
    
    ErrorCodeEnum(int code, String remark) {
        this.code = code;
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }

    public int getCode() {
        return code;
    }

    public enum ErrorCodedEnum {

    }
}
