package com.lqs.mall.service;

import com.lqs.mall.domain.CartPromotionItem;
import com.lqs.mall.domain.SmsCouponHistoryDetail;

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
}
