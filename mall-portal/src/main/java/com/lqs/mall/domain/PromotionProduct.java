package com.lqs.mall.domain;

import com.lqs.mall.model.PmsProduct;
import com.lqs.mall.model.PmsProductFullReduction;
import com.lqs.mall.model.PmsProductLadder;
import com.lqs.mall.model.PmsSkuStock;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 促销商品信息，包括sku、打折优惠、满减优惠
 * Created by 刘千山 on 2023/7/4/004-16:19
 */
@Getter
@Setter
public class PromotionProduct extends PmsProduct {
    //商品库存信息
    private List<PmsSkuStock> skuStockList;
    //商品打折信息
    private List<PmsProductLadder> productLadderList;
    //商品满减信息
    private List<PmsProductFullReduction> productFullReductionList;
}
