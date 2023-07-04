package com.lqs.mall.dto;

import com.lqs.mall.model.OmsCompanyAddress;
import com.lqs.mall.model.OmsOrderReturnApply;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 退货详情封装
 * Created by 刘千山 on 2023/7/4/004-14:58
 */
public class OmsOrderReturnApplyResult extends OmsOrderReturnApply {
    @Getter
    @Setter
    @ApiModelProperty(value = "公司收货地址")
    private OmsCompanyAddress companyAddress;
}
