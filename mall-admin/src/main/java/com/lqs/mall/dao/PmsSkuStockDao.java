package com.lqs.mall.dao;

import com.lqs.mall.model.PmsSkuStock;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 后台 商品SKU 管理 自定义dao
 * Created by 刘千山 on 2023/7/3/003-16:26
 */
public interface PmsSkuStockDao {
    /**
     * 批量插入操作
     */
    int insertList(@Param("list") List<PmsSkuStock> skuStockList);

    /**
     * 批量插入或替换操作
     */
    int replaceList(List<PmsSkuStock> skuStockList);
}
