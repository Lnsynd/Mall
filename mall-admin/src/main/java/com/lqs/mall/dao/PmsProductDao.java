package com.lqs.mall.dao;

import com.lqs.mall.dto.PmsProductResult;
import io.lettuce.core.dynamic.annotation.Param;

/**
 * 商品管理自定义Dao
 * Created by 刘千山 on 2023/7/3/003-16:36
 */
public interface PmsProductDao {
    PmsProductResult getUpdateInfo(@Param("id") Long id);
}
