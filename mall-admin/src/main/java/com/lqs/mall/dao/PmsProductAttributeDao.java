package com.lqs.mall.dao;

import com.lqs.mall.dto.ProductAttrInfo;

import java.util.List;

/**
 * 商品属性管理 自定义dao
 * Created by 刘千山 on 2023/7/3/003-20:16
 */
public interface PmsProductAttributeDao {
    /**
     * 获取商品属性信息
     */
    List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);
}
