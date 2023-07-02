package com.lqs.mall.dao;

import com.lqs.mall.model.UmsMenu;
import com.lqs.mall.model.UmsResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 刘千山 on 2023/7/2/002-10:58
 */
public interface UmsRoleDao {
    /**
     * 根据后台用户ID获取菜单
     */
    List<UmsMenu> getMenuList(@Param("adminId")Long adminId);

    /**
     * 根据角色ID获取菜单
     */
    List<UmsMenu> getMenuListByRoleId(Long roleId);

    /**
     * 根据角色ID获取资源列表
     */
    List<UmsResource> getResourceListByRoleId(Long roleId);
}
