package com.lqs.mall.service;

import com.lqs.mall.model.UmsMenu;
import com.lqs.mall.model.UmsResource;
import com.lqs.mall.model.UmsRole;
import org.springframework.transaction.annotation.Transactional;

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

    /**
     * 添加角色
     */
    int create(UmsRole role);

    /**
     * 修改角色信息
     */
    int update(Long id, UmsRole role);
    /**
     * 删除角色信息
     */
    int delete(List<Long> ids);

    /**
     * 获取全部角色信息
     */
    List<UmsRole> list();

    /**
     * 分页获取全部角色信息
     */
    List<UmsRole> list(String keyword,Integer pageSize,Integer pageNum);

    /**
     * 获取角色拥有的菜单信息
     */
    List<UmsMenu> listMenu(Long roleId);
    /**
     * 获取角色拥有的资源信息
     */
    List<UmsResource> listResource(Long roleId);
    /**
     * 给角色分配菜单
     */
    @Transactional
    int allocMenu(Long roleId, List<Long> menuIds);

    /**
     * 给角色分配资源
     */
    @Transactional
    int allocResource(Long roleId, List<Long> resourceIds);
}
