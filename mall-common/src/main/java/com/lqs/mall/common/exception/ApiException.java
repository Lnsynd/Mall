package com.lqs.mall.common.exception;

import com.lqs.mall.common.api.IErrorCode;

/**
 * 自定义API异常
 * Created by 刘千山 on 2023/6/30/030-09:44
 */
public class ApiException extends RuntimeException {

    private IErrorCode errorCode;

    public ApiException(IErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ApiException(String message) {
        super(message);
    }

    public ApiException(Throwable cause) {
        super(cause);
    }


    public ApiException(String message, Throwable cause) {
        super(message, cause);
    }


    public IErrorCode getErrorCode(){
        return errorCode;
    }

}
