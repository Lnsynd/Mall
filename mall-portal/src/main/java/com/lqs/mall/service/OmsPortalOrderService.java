package com.lqs.mall.service;

import com.lqs.mall.common.api.CommonPage;
import com.lqs.mall.domain.ConfirmOrderResult;
import com.lqs.mall.domain.OmsOrderDetail;
import com.lqs.mall.domain.OrderParam;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 前台订单管理
 * Created by 刘千山 on 2023/7/4/004-17:41
 */
public interface OmsPortalOrderService {
    /**
     * 生成购物车信息生成确认单
     */
    ConfirmOrderResult generateConfirmOrder(List<Long> cartIds);

    /**
     * 根据提交信息生成订单
     */
    @Transactional
    Map<String, Object> generateOrder(OrderParam orderParam);

    /**
     * 支付成功后的回调
     */
    @Transactional
    Integer paySuccess(Long orderId, Integer payType);

    /**
     * 发送延迟消息取消订单
     */
    void sendDelayMessageCancelOrder(Long orderId);

    /**
     * 取消单个超时订单
     */
    @Transactional
    void cancelOrder(Long orderId);

    /**
     * 自动取消超时订单
     */
    @Transactional
    Integer cancelTimeOutOrder();

    /**
     * 按照状态分页获取订单列表
     */
    CommonPage<OmsOrderDetail> list(Integer status, Integer pageNum, Integer pageSize);

    /**
     * 根据id获取订单详情
     */
    OmsOrderDetail detail(Long orderId);

    /**
     * 用户确认收货
     */
    void confirmReceiveOrder(Long orderId);

    /**
     * 用户删除订单
     */
    void deleteOrder(Long orderId);
}
