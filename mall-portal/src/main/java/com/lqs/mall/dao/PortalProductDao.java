package com.lqs.mall.dao;

import com.lqs.mall.domain.CartProduct;
import com.lqs.mall.domain.PromotionProduct;
import com.lqs.mall.model.SmsCoupon;
import org.apache.ibatis.annotations.Param;

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
    List<SmsCoupon> getAvailableCouponList(@Param("id")Long id, Long productCategoryId);

    /**
     * 获取促销商品信息列表
     */
    List<PromotionProduct> getPromotionProductList(@Param("ids")List<Long> productIdList);

    /**
     * 获取指定商品的属性和规格信息
     */
    CartProduct getCartProduct(@Param("id")Long productId);
}
