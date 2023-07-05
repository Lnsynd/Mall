package com.lqs.mall.service;

import com.lqs.mall.dto.SmsCouponParam;
import com.lqs.mall.model.SmsCoupon;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 优惠券管理Service
 * Created by 刘千山 on 2023/7/5/005-08:35
 */
public interface SmsCouponService {

    /**
     * 添加优惠券
     */
    @Transactional
    int create(SmsCouponParam couponParam);

    /**
     * 删除优惠券
     */
    @Transactional
    int delete(Long id);

    /**
     * 修改优惠券信息
     */
    @Transactional
    int update(Long id, SmsCouponParam couponParam);

    /**
     * 分页获取优惠券列表
     */
    List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum);

    /**
     * 获取单个优惠券的信息(包含绑定关系)
     */
    SmsCouponParam getItem(Long id);
}
