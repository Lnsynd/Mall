package com.lqs.mall.service;

import com.lqs.mall.dto.*;
import com.lqs.mall.model.OmsOrder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 订单管理Service
 * Created by 刘千山 on 2023/7/4/004-10:54
 */
public interface OmsOrderService {

    /**
     * 分页查询订单
     * @param queryParam 查询参数
     */
    List<OmsOrder> list(OmsOrderQueryParam queryParam, Integer pageSize, Integer pageNum);

    /**
     * 订单批量发货
     */
    @Transactional
    int delivery(List<OmsOrderDeliveryParam> deliveryParamList);


    /**
     * 批量关闭订单
     * @param ids 订单id
     * @param note 备注
     */
    @Transactional
    int close(List<Long> ids, String note);

    /**
     * 批量删除订单
     */
    int delete(List<Long> ids);

    /**
     * 获取订单详情
     */
    OmsOrderDetail detail(Long id);

    /**
     * 修改订单收货人信息
     */
    @Transactional
    int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam);

    /**
     * 修改订单运费信息
     */
    @Transactional
    int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam);

    /**
     * 修改订单备注信息
     */
    @Transactional
    int updateNote(Long id, String note, Integer status);
}
