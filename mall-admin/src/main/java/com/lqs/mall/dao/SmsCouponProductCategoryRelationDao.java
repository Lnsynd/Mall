package com.lqs.mall.dao;

import com.lqs.mall.model.SmsCouponProductCategoryRelation;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;

/**
 * 优惠券 与 商品分类 管理 自定义dao
 * Created by 刘千山 on 2023/7/5/005-08:50
 */
public interface SmsCouponProductCategoryRelationDao {
    /**
     * 批量创建 优惠券和商品分类 关系
     */
    int insertList(@Param("list") List<SmsCouponProductCategoryRelation> productCategoryRelationList);
}
