package com.lqs.mall.service;

import com.lqs.mall.model.SmsCouponHistory;

import java.util.List;

/**
 * 优惠券领取 管理Service
 * Created by 刘千山 on 2023/7/5/005-09:07
 */
public interface SmsCouponHistoryService {

    /**
     * 根据优惠券id、使用状态、订单编号分页获取优惠券信息
     * @param couponId 优惠券id
     * @param useStatus 使用状态
     * @param orderSn 订单编号
     */
    List<SmsCouponHistory> list(Long couponId, Integer useStatus, String orderSn, Integer pageSize, Integer pageNum);
}
