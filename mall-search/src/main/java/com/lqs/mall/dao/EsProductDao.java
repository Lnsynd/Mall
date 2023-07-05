package com.lqs.mall.dao;

import com.lqs.mall.domain.EsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 搜索商品管理自定义Dao
 * Created by 刘千山 on 2023/7/5/005-15:10
 */
public interface EsProductDao {
    /**
     * 获取指定id 的搜索物品
     */
    List<EsProduct> getAllEsProductList(@Param("id") Long id);
}
