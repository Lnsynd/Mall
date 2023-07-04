package com.lqs.mall.domain;

import com.lqs.mall.model.PmsProduct;
import com.lqs.mall.model.PmsProductAttribute;
import com.lqs.mall.model.PmsSkuStock;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 购物车中带有属性和规格的商品信息
 * Created by 刘千山 on 2023/7/4/004-17:27
 */
@Getter
@Setter
public class CartProduct extends PmsProduct {
    @ApiModelProperty("商品属性列表")
    private List<PmsProductAttribute> productAttributeList;
    @ApiModelProperty("商品SKU库存列表")
    private List<PmsSkuStock> skuStockList;
}
