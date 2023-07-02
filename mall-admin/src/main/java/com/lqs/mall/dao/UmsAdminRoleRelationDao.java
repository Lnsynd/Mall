package com.lqs.mall.dao;

import com.lqs.mall.model.UmsAdminRoleRelation;
import com.lqs.mall.model.UmsResource;
import com.lqs.mall.model.UmsRole;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * 后台 用户与角色 关系自定义DAO
 * Created by 刘千山 on 2023/6/30/030-15:37
 */
public interface UmsAdminRoleRelationDao {
    /**
     * 获取用户所有可访问资源
     */
    List<UmsResource> getResourceList(@Param("adminId") Long adminId);

    /**
     * 获取用于所有角色
     */
    List<UmsRole> getRoleList(@Param("adminId")Long adminId);

    /**
     * 批量插入用户角色关系
     */
    int insertList(@Param("list") List<UmsAdminRoleRelation> list);
}
