package com.lqs.mall.service;

import com.lqs.mall.model.UmsMenu;

import java.util.List;

/**
 * 后台角色管理
 * Created by 刘千山 on 2023/7/2/002-10:53
 */
public interface UmsRoleService {
    /**
     * 根据id获取对应的 菜单列表
     */
    List<UmsMenu> getMenuList(Long adminId);
}
