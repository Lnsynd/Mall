package com.lqs.mall.service;

import com.lqs.mall.model.OmsOrderSetting;

/**
 * 订单设置 Service
 * Created by 刘千山 on 2023/7/4/004-14:46
 */
public interface OmsOrderSettingService {
    /**
     * 获取指定订单设置
     */
    OmsOrderSetting getItem(Long id);

    /**
     * 修改指定订单设置
     */
    int update(Long id, OmsOrderSetting orderSetting);
}
