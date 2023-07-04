package com.lqs.mall.dao;

import com.lqs.mall.domain.OmsOrderDetail;
import com.lqs.mall.model.OmsOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单管理 自定义dao
 * Created by 刘千山 on 2023/7/4/004-19:39
 */
public interface PortalOrderDao {
    /**
     * 获取订单及下单商品详情
     */
    OmsOrderDetail getDetail(@Param("orderId") Long orderId);


    /**
     * 修改sku_stock库存表的锁定库存和真实库存
     */
    int updateSkuStock(@Param("itemList")List<OmsOrderItem> orderItemList);

    /**
     * 解除取消订单的库存锁定
     */
    int releaseSkuStockLock(@Param("itemList") List<OmsOrderItem> orderItemList);

    /**
     * 获取超时订单
     * @param minute 超时时间（分）
     */
    List<OmsOrderDetail> getTimeOutOrders(@Param("minute") Integer minute);

    /**
     * 批量修改订单状态
     */
    int updateOrderStatus(@Param("ids") List<Long> ids,@Param("status") Integer status);
}
