package com.lqs.mall.common.api;

/**
 * API返回码接口
 * Created by 刘千山 on 2023/6/29/029-19:24
 */
public interface IErrorCode {


    /**
     * @return  返回状态码
     */
    long getCode();

    /**
     * @return 返回提示信息
     */
    String getMessage();

}
