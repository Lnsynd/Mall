package com.lqs.mall.common.exception;

import com.lqs.mall.common.api.CommonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 * Created by 刘千山 on 2023/6/30/030-09:41
 */
@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(value = ApiException.class)
    public CommonResult<Object> handle(ApiException e){
        if(e.getErrorCode()!=null){
            return CommonResult.failed(e.getErrorCode());
        }
        return CommonResult.failed(e.getMessage());
    }

}
