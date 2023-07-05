package com.lqs.mall.dao;

import com.lqs.mall.dto.SmsCouponParam;
import org.apache.ibatis.annotations.Param;

/**
 * 优惠券管理 自定义DAO
 * Created by 刘千山 on 2023/7/5/005-09:03
 */
public interface SmsCouponDao {

    /**
     * 获取单个优惠券详情(包含绑定关系)
     */
    SmsCouponParam getItem(@Param("id")Long id);
}
