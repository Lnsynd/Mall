package com.lqs.mall.service;

import com.lqs.mall.domain.CartPromotionItem;
import com.lqs.mall.domain.SmsCouponHistoryDetail;
import com.lqs.mall.model.SmsCoupon;
import com.lqs.mall.model.SmsCouponHistory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户优惠券管理Service
 * Created by 刘千山 on 2023/7/4/004-17:50
 */
public interface UmsMemberCouponService {
    /**
     * 根据购物车信息获取可用优惠券
     */
    List<SmsCouponHistoryDetail> listCart(List<CartPromotionItem> cartPromotionItemList, Integer type);

    /**
     * 用户领取优惠券
     */
    @Transactional
    void add(Long couponId);

    /**
     * 获取优惠券历史列表
     */
    List<SmsCouponHistory> listHistory(Integer useStatus);

    /**
     * 获取优惠券列表
     */
    List<SmsCoupon> list(Integer useStatus);

    /**
     * 获取当前商品的优惠券
     */
    List<SmsCoupon> listByProduct(Long productId);
}
