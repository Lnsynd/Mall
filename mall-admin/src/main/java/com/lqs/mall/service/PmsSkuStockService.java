package com.lqs.mall.service;

import com.lqs.mall.model.PmsSkuStock;

import java.util.List;

/**
 * sku商品库存管理
 * Created by 刘千山 on 2023/7/3/003-18:55
 */
public interface PmsSkuStockService {

    /**
     * 利用产品id和skuCode关键字模糊搜索
     */
    List<PmsSkuStock> getList(Long pid, String keyword);

    /**
     * 批量更新sku库存信息
     */
    int update(Long pid, List<PmsSkuStock> skuStockList);
}
