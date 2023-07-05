package com.lqs.mall.dao;

import com.lqs.mall.model.SmsCouponProductRelation;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优惠券与商品 关系 自定义 dao
 * Created by 刘千山 on 2023/7/5/005-08:46
 */
public interface SmsCouponProductRelationDao {
    /**
     * 批量插入优惠券与商品关系
     */
    int insertList(@Param("list") List<SmsCouponProductRelation> productRelationList);
}
