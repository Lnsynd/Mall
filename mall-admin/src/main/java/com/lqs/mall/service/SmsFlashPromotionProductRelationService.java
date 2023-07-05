package com.lqs.mall.service;

import com.lqs.mall.dto.SmsFlashPromotionProduct;
import com.lqs.mall.model.SmsFlashPromotionProductRelation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 场次与商品关系 管理Service
 * Created by 刘千山 on 2023/7/4/004-21:07
 */
public interface SmsFlashPromotionProductRelationService {
    /**
     * 根据活动id和场次id获取  商品数量
     * @param flashPromotionId        限时购id
     * @param flashPromotionSessionId 限时购场次id
     */
    long getCount(Long flashPromotionId, Long flashPromotionSessionId);


    /**
     * 批量添加关联
     */
    @Transactional
    int create(List<SmsFlashPromotionProductRelation> relationList);

    /**
     * 修改关联信息
     */
    int update(Long id, SmsFlashPromotionProductRelation relation);

    /**
     * 删除关联信息
     */
    int delete(Long id);

    /**
     * 获取关联商品的促销信息
     */
    SmsFlashPromotionProductRelation getItem(Long id);

    /**
     * 分页查询相关商品及限时购促销信息
     *
     * @param flashPromotionId        限时购id
     * @param flashPromotionSessionId 限时购场次id
     */
    List<SmsFlashPromotionProduct> list(Long flashPromotionId, Long flashPromotionSessionId, Integer pageSize, Integer pageNum);
}
