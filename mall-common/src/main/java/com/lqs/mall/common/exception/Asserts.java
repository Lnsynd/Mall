package com.lqs.mall.common.exception;

import com.lqs.mall.common.api.IErrorCode;

/**
 * 断言处理类,用于抛出API异常
 * Created by 刘千山 on 2023/6/30/030-09:49
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode iErrorCode) {
        throw new ApiException(iErrorCode);
    }
}
