package com.lqs.mall.service;

import com.lqs.mall.model.UmsMember;

/**
 * Created by 刘千山 on 2023/7/3/003-20:42
 */
public interface UmsMemberCacheService {
    /**
     * 获取验证码
     */
    String getAuthCode(String telephone);

    /**
     * 获取用户
     */
    UmsMember getMember(String username);

    /**
     * 设置用户缓存
     */
    void setMember(UmsMember member);

    /**
     * 设置验证码
     * @param telephone 手机号
     * @param authCode 验证码
     */
    void setAuthCode(String telephone, String authCode);

    /**
     * 删除用户的缓存信息
     * @param id 用户id
     */
    void delMember(Long id);
}
