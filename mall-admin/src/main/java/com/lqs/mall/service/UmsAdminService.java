package com.lqs.mall.service;

import com.lqs.mall.dto.UmsAdminParam;
import com.lqs.mall.dto.UpdateAdminPasswordParam;
import com.lqs.mall.model.UmsAdmin;
import com.lqs.mall.model.UmsResource;
import com.lqs.mall.model.UmsRole;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

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


    /**
     * 更新token信息
     */
    String refreshToken(String oldToken);

    /**
     * 后台获取用户列表
     */
    List<UmsAdmin> list(String keyword, Integer pageSize, Integer pageNum);

    /**
     * 根据id获取用户
     */
    UmsAdmin getItem(Long id);

    /**
     * 修改用户信息
     * @param id id
     * @param admin 要修改的条件
     */
    int update(Long id, UmsAdmin admin);

    /**
     * 修改用户密码
     */
    int updatePassword(UpdateAdminPasswordParam updatePasswordParam);

    /**
     * 获取缓存服务
     */
    UmsAdminCacheService getCacheService();


    /**
     * 根据id删除用户信息
     */
    int delete(Long id);

    /**
     * 修改用户角色关系
     */
    @Transactional
    int updateRole(Long adminId, List<Long> roleIds);
}
