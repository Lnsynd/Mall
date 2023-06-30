package com.lqs.mall.service;

import com.lqs.mall.dto.UmsAdminParam;
import com.lqs.mall.model.UmsAdmin;
import com.lqs.mall.model.UmsResource;
import com.lqs.mall.model.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 后台用户管理Service
 * Created by 刘千山 on 2023/6/30/030-15:12
 */
public interface UmsAdminService {

    /**
     * 根据用户名获取用户
     */
    UmsAdmin getAdminByUsername(String username);

    /**
     *  用户注册
     */
    UmsAdmin register(UmsAdminParam umsAdminParam);

    /**
     * 用户登录
     */

    String login(String username, String password);


    /**
     * 根据用户id获取用户角色 列表
     */
    List<UmsRole> getRoleList(Long adminId);

    /**
     * 根据ID获取用户资源 列表
     */
    List<UmsResource> getResouceList(Long adminId);


    /**
     * 获取用户信息
     */
    UserDetails loadUserByUsername(String username);


}
