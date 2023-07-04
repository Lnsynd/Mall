package com.lqs.mall.service;

import com.lqs.mall.model.UmsMember;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

/**
 * 会员管理Service
 * Created by 刘千山 on 2023/7/3/003-20:37
 */
public interface UmsMemberService {
    /**
     * 根据用户名获取会员
     */
    UmsMember getByUsername(String username);

    /**
     * 根据会员编号获取会员
     */
    UmsMember getById(Long id);

    /**
     * 根据用户名获取用户信息
     */
    UserDetails loadUserByUsername(String username);
    /**
     * 用户注册
     */
    @Transactional
    void register(String username, String password, String telephone, String authCode);

    /**
     * 用户登录
     * return  token
     */
    String login(String username, String password);

    /**
     * 获取当前用户信息
     */
    UmsMember getCurrentMember();

    /**
     * 获取验证码
     * @param telephone 手机号
     * @return 验证码
     */
    String generateAuthCode(String telephone);

    /**
     * 会员修改密码
     * @param telephone 手机号
     * @param password 密码
     * @param authCode 验证码
     */
    @Transactional
    void updatePassword(String telephone, String password, String authCode);

    /**
     * 刷新token
     */
    String refreshToken(String token);

    /**
     * 根据会员id修改会员积分
     */
    void updateIntegration(Long id, int integration);
}
