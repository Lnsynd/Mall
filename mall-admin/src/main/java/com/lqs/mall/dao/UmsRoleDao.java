package com.lqs.mall.dao;

import com.lqs.mall.model.UmsMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 刘千山 on 2023/7/2/002-10:58
 */
public interface UmsRoleDao {
    List<UmsMenu> getMenuList(@Param("adminId")Long adminId);
}
