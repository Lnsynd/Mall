package com.lqs.mall.dto;

import com.lqs.mall.model.OmsOrder;
import com.lqs.mall.model.OmsOrderItem;
import com.lqs.mall.model.OmsOrderOperateHistory;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 获取的订单详情
 * Created by 刘千山 on 2023/7/4/004-11:27
 */
public class OmsOrderDetail extends OmsOrder {

    @Getter
    @Setter
    @ApiModelProperty("订单商品列表")
    private List<OmsOrderItem> orderItemList;
    @Getter
    @Setter
    @ApiModelProperty("订单操作记录列表")
    private List<OmsOrderOperateHistory> historyList;
}
