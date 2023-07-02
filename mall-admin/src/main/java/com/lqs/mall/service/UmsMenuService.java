package com.lqs.mall.service;

import com.lqs.mall.dto.UmsMenuNode;
import com.lqs.mall.model.UmsMenu;

import java.util.List;

/**
 * 后台菜单Service
 * Created by 刘千山 on 2023/7/2/002-15:22
 */
public interface UmsMenuService {
    /**
     * 新建菜单
     */
    int create(UmsMenu umsMenu);
    /**
     * 修改菜单
     */
    int update(Long id, UmsMenu umsMenu);

    /**
     * 根据id获取菜单
     */
    UmsMenu getItem(Long id);

    /**
     * 根据id删除菜单
     */
    int delete(Long id);

    /**
     * 分页查询菜单列表
     */
    List<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 树形结构返回全部菜单列表
     */
    List<UmsMenuNode> treeList();

    /**
     * 修改菜单显示状态
     */
    int updateHidden(Long id, Integer hidden);
}
