package com.lqs.mall.dao;

import com.lqs.mall.dto.SmsFlashPromotionProduct;

import java.util.List;

/**
 * 限时购活动和商品关系 管理 自定义dao
 * Created by 刘千山 on 2023/7/4/004-21:33
 */
public interface SmsFlashPromotionProductRelationDao {
    /**
     * 根据限时购活动id和场次id获取  限时购以及商品信息
     * @param flashPromotionId 限时购活动id
     * @param flashPromotionSessionId 限时购场次id
     */
    List<SmsFlashPromotionProduct> getList(Long flashPromotionId, Long flashPromotionSessionId);
}
