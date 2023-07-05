package com.lqs.mall.dao;

import com.lqs.mall.domain.FlashPromotionProduct;
import com.lqs.mall.model.CmsSubject;
import com.lqs.mall.model.PmsBrand;
import com.lqs.mall.model.PmsProduct;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 首页内容管理自定义Dao
 * Created by 刘千山 on 2023/7/4/004-09:05
 */
public interface HomeDao {
    /**
     * 获取推荐品牌
     */
    List<PmsBrand> getRecommendBrandList(@Param("offset")int offset,@Param("limit") Integer pageSize);

    /**
     * 获取新品推荐
     */
    List<PmsProduct> getNewProductList(@Param("offset") Integer offset,@Param("limit") Integer limit);
    /**
     * 获取人气推荐
     */
    List<PmsProduct> getHotProductList(@Param("offset") Integer offset,@Param("limit") Integer limit);

    /**
     * 获取推荐专题
     */
    List<CmsSubject> getRecommendSubjectList(@Param("offset") Integer offset, @Param("limit") Integer limit);

    /**
     * 获取秒杀活动的商品列表
     * @param flashPromotionId 秒杀活动的id
     * @param sessionId 秒杀活动的场次id
     */
    List<FlashPromotionProduct> getFlashProductList(@Param("flashPromotionId") Long flashPromotionId, @Param("sessionId") Long sessionId);
}
