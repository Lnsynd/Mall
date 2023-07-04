package com.lqs.mall.dao;

import com.lqs.mall.model.SmsCoupon;

import java.util.List;

/**
 * 前台购物车商品管理自定义Dao
 * Created by 刘千山 on 2023/7/4/004-09:43
 */
public interface PortalProductDao {
    /**
     * 获取可用优惠券
     * @param id 商品id
     * @param productCategoryId 商品分类id
     */
    List<SmsCoupon> getAvailableCouponList(Long id, Long productCategoryId);
}
