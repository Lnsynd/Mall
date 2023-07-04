package com.lqs.mall.domain;

import com.lqs.mall.model.SmsCoupon;
import com.lqs.mall.model.SmsCouponHistory;
import com.lqs.mall.model.SmsCouponProductCategoryRelation;
import com.lqs.mall.model.SmsCouponProductRelation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 优惠券领取历史详情（包括优惠券信息和关联关系）
 * Created by 刘千山 on 2023/7/4/004-17:42
 */
@Getter
@Setter
public class SmsCouponHistoryDetail extends SmsCouponHistory {
    @ApiModelProperty("相关优惠券信息")
    private SmsCoupon coupon;
    @ApiModelProperty("优惠券关联商品")
    private List<SmsCouponProductRelation> productRelationList;
    @ApiModelProperty("优惠券关联商品分类")
    private List<SmsCouponProductCategoryRelation> categoryRelationList;
}
