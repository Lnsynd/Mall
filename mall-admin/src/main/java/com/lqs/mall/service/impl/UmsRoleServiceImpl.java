package com.lqs.mall.service.impl;

import com.lqs.mall.dao.UmsRoleDao;
import com.lqs.mall.model.UmsMenu;
import com.lqs.mall.service.UmsRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 后台角色管理实现类
 * Created by 刘千山 on 2023/7/2/002-10:53
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {

    @Autowired
    private UmsRoleDao roleDao;

    /**
     * 根据id获取对应的 菜单列表
     */
    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        return roleDao.getMenuList(adminId);
    }
}
