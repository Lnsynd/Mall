package com.lqs.mall.service;

import com.lqs.mall.model.UmsAdmin;
import com.lqs.mall.model.UmsResource;

import java.util.List;

/**
 * 后台用户缓存操作Service
 * Created by 刘千山 on 2023/7/2/002-13:19
 */
public interface UmsAdminCacheService {
    /**
     * 获取缓存后台用户信息
     */
    UmsAdmin getAdmin(String username);

    /**
     * 设置缓存后台用户信息
     */
    void setAdmin(UmsAdmin admin);

    /**
     * 获取缓存后台用户资源列表
     */
    List<UmsResource> getResourceList(Long adminId);

    /**
     * 设置缓存后台用户资源列表
     */
    void setResourceList(Long adminId,List<UmsResource> resourceList);

    /**
     * 删除后台用户缓存
     */
    void delAdmin(Long id);

    /**
     * 删除后台用户资源列表缓存
     */
    void delResourceList(Long id);

    /**
     * 删除角色对应的资源列表
     */
    void delResourceListByRoleIds(List<Long> ids);

    /**
     * 当角色相关资源信息改变时删除相关后台用户缓存
     */
    void delResourceListByRole(Long roleId);
}
