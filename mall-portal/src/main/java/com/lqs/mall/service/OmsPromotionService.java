package com.lqs.mall.service;

import com.lqs.mall.domain.CartPromotionItem;
import com.lqs.mall.model.OmsCartItem;

import java.util.List;

/**
 * 订单优惠Service
 * Created by 刘千山 on 2023/7/4/004-16:09
 */
public interface OmsPromotionService {

    /**
     * 计算购物车优惠
     * @param cartItemList 购物车
     */
    List<CartPromotionItem> calcCartPromotion(List<OmsCartItem> cartItemList);
}
