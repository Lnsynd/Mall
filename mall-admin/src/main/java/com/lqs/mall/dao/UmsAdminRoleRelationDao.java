package com.lqs.mall.dao;

import com.lqs.mall.model.UmsResource;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * 后台 用户与角色 关系自定义DAO
 * Created by 刘千山 on 2023/6/30/030-15:37
 */
public interface UmsAdminRoleRelationDao {
    List<UmsResource> getResourceList(@Param("adminId") Long adminId);
}
