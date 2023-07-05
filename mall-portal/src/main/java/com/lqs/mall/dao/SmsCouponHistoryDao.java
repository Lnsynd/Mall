package com.lqs.mall.dao;

import com.lqs.mall.domain.SmsCouponHistoryDetail;
import com.lqs.mall.model.SmsCoupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员优惠券领取记录管理自定义Dao
 * Created by 刘千山 on 2023/7/4/004-17:54
 */
public interface SmsCouponHistoryDao {
    /**
     * 获取优惠券历史详情
     */
    List<SmsCouponHistoryDetail> getDetailList(@Param("memberId") Long memberId);

    /**
     * 获取指定会员优惠券列表
     */
    List<SmsCoupon> getCouponList(@Param("memberId") Long id, @Param("useStatus") Integer useStatus);
}
