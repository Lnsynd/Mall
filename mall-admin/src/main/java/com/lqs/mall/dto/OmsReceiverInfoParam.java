package com.lqs.mall.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 修改订单收货人信息 参数
 * Created by 刘千山 on 2023/7/4/004-11:38
 */
@Getter
@Setter
public class OmsReceiverInfoParam {
    @ApiModelProperty(value = "订单ID")
    private Long orderId;
    @ApiModelProperty(value = "收货人姓名")
    private String receiverName;
    @ApiModelProperty(value = "收货人电话")
    private String receiverPhone;
    @ApiModelProperty(value = "收货人邮编")
    private String receiverPostCode;
    @ApiModelProperty(value = "详细地址")
    private String receiverDetailAddress;
    @ApiModelProperty(value = "省份/直辖市")
    private String receiverProvince;
    @ApiModelProperty(value = "城市")
    private String receiverCity;
    @ApiModelProperty(value = "区")
    private String receiverRegion;
    @ApiModelProperty(value = "订单状态：0->待付款；1->待发货；2->已发货；3->已完成；4->已关闭；5->无效订单")
    private Integer status;
}
