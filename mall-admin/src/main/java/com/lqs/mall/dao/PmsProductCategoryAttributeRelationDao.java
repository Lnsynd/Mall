package com.lqs.mall.dao;

import com.lqs.mall.model.PmsProductCategoryAttributeRelation;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * 后台-商品分类与属性 关系 自定义dao
 * Created by 刘千山 on 2023/7/3/003-15:04
 */
public interface PmsProductCategoryAttributeRelationDao {
    /**
     * 插入 分类与 属性关系
     */
    int insertList(@Param("list") List<PmsProductCategoryAttributeRelation> relationList);
}
