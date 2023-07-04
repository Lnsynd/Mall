package com.lqs.mall.dao;

import com.lqs.mall.dto.OmsOrderDeliveryParam;
import com.lqs.mall.dto.OmsOrderDetail;
import com.lqs.mall.dto.OmsOrderQueryParam;
import com.lqs.mall.model.OmsOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 刘千山 on 2023/7/4/004-10:57
 */
public interface OmsOrderDao {
    /**
     * 条件查询订单
     */
    List<OmsOrder> getList(@Param("queryParam")OmsOrderQueryParam queryParam);

    /**
     * 批量发货
     */
    int delivery(@Param("list") List<OmsOrderDeliveryParam> deliveryParamList);

    /**
     * 获取订单详情
     */
    OmsOrderDetail getDetail(Long id);
}
