package com.lqs.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.lqs.mall.dao.OmsOrderDao;
import com.lqs.mall.dao.OmsOrderOperateHistoryDao;
import com.lqs.mall.dto.*;
import com.lqs.mall.mapper.OmsOrderMapper;
import com.lqs.mall.mapper.OmsOrderOperateHistoryMapper;
import com.lqs.mall.model.OmsOrder;
import com.lqs.mall.model.OmsOrderExample;
import com.lqs.mall.model.OmsOrderOperateHistory;
import com.lqs.mall.service.OmsOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 订单管理实现类
 * Created by 刘千山 on 2023/7/4/004-10:54
 */
@Service
public class OmsOrderServiceImpl implements OmsOrderService {

    @Autowired
    private OmsOrderOperateHistoryMapper orderOperateHistoryMapper;

    @Autowired
    private OmsOrderMapper orderMapper;

    @Autowired
    private OmsOrderOperateHistoryDao orderOperateHistoryDao;

    @Autowired
    private OmsOrderDao orderDao;

    @Override
    public List<OmsOrder> list(OmsOrderQueryParam queryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        return orderDao.getList(queryParam);
    }

    @Override
    public int delivery(List<OmsOrderDeliveryParam> deliveryParamList) {
        // 批量发货
        int count = orderDao.delivery(deliveryParamList);
        // 添加操作记录
        List<OmsOrderOperateHistory> operateHistory = deliveryParamList.stream().map(param -> {
            OmsOrderOperateHistory history = new OmsOrderOperateHistory();
            history.setOrderId(param.getOrderId());
            history.setCreateTime(new Date());
            history.setOperateMan("后台管理员");
            history.setOrderStatus(2);
            history.setNote("完成发货");
            return history;
        }).collect(Collectors.toList());
        orderOperateHistoryDao.insertList(operateHistory);
        return count;
    }

    @Override
    public int close(List<Long> ids, String note) {
        // 给id在ids中的 订单修改状态为 关闭
        OmsOrder order = new OmsOrder();
        order.setStatus(4);
        OmsOrderExample example = new OmsOrderExample();
        example.createCriteria().andIdIn(ids).andDeleteStatusEqualTo(0);
        int count = orderMapper.updateByExampleSelective(order, example);
        List<OmsOrderOperateHistory> historyList = ids.stream()
                .map(orderId -> {
                    OmsOrderOperateHistory history = new OmsOrderOperateHistory();
                    history.setOrderId(orderId);
                    history.setCreateTime(new Date());
                    history.setOperateMan("后台管理员");
                    history.setOrderStatus(4);
                    history.setNote("订单关闭:" + note);
                    return history;
                }).collect(Collectors.toList());
        orderOperateHistoryDao.insertList(historyList);
        return count;
    }

    @Override
    public int delete(List<Long> ids) {
        OmsOrder record = new OmsOrder();
        record.setDeleteStatus(1);
        OmsOrderExample example = new OmsOrderExample();
        example.createCriteria().andDeleteStatusEqualTo(0).andIdIn(ids);
        return orderMapper.updateByExampleSelective(record, example);
    }

    @Override
    public OmsOrderDetail detail(Long id) {
        return orderDao.getDetail(id);
    }

    @Override
    public int updateReceiverInfo(OmsReceiverInfoParam receiverInfoParam) {
        // 更新订单 收货人 信息
        OmsOrder order = new OmsOrder();
        order.setId(receiverInfoParam.getOrderId());
        order.setReceiverName(receiverInfoParam.getReceiverName());
        order.setReceiverPhone(receiverInfoParam.getReceiverPhone());
        order.setReceiverPostCode(receiverInfoParam.getReceiverPostCode());
        order.setReceiverDetailAddress(receiverInfoParam.getReceiverDetailAddress());
        order.setReceiverProvince(receiverInfoParam.getReceiverProvince());
        order.setReceiverCity(receiverInfoParam.getReceiverCity());
        order.setReceiverRegion(receiverInfoParam.getReceiverRegion());
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(receiverInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(receiverInfoParam.getStatus());
        history.setNote("修改收货人信息");
        orderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    public int updateMoneyInfo(OmsMoneyInfoParam moneyInfoParam) {
        // 更新订单表中的数据
        OmsOrder order = new OmsOrder();
        order.setId(moneyInfoParam.getOrderId());
        order.setFreightAmount(moneyInfoParam.getFreightAmount());
        order.setDiscountAmount(moneyInfoParam.getDiscountAmount());
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        //插入操作记录
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(moneyInfoParam.getOrderId());
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(moneyInfoParam.getStatus());
        history.setNote("修改费用信息");
        orderOperateHistoryMapper.insert(history);
        return count;
    }

    @Override
    public int updateNote(Long id, String note, Integer status) {
        OmsOrder order = new OmsOrder();
        order.setId(id);
        order.setNote(note);
        order.setModifyTime(new Date());
        int count = orderMapper.updateByPrimaryKeySelective(order);
        OmsOrderOperateHistory history = new OmsOrderOperateHistory();
        history.setOrderId(id);
        history.setCreateTime(new Date());
        history.setOperateMan("后台管理员");
        history.setOrderStatus(status);
        history.setNote("修改备注信息："+note);
        orderOperateHistoryMapper.insert(history);
        return count;
    }
}
