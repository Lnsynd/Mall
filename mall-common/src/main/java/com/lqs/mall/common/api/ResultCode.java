package com.lqs.mall.common.api;

/**
 * API返回码封装类
 * Created by 刘千山 on 2023/6/29/029-19:24
 */
public enum ResultCode implements IErrorCode {
    SUCCESS(200, "操作成功"),
    FAILED(500, "操作失败"),
    VALIDATE_FAILED(404, "参数验证错误"),
    UNAUTHORIZED(401, "未登录或者验证信息已过期"),
    FORBIDDEN(403, "没有相关权限");

    private long code;
    private String message;

    ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    public long getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}
