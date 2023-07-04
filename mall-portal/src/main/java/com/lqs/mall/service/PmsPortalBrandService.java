package com.lqs.mall.service;

import com.lqs.mall.common.api.CommonPage;
import com.lqs.mall.model.PmsBrand;
import com.lqs.mall.model.PmsProduct;

import java.util.List;

/**
 * 首页品牌推荐管理
 * Created by 刘千山 on 2023/7/4/004-09:03
 */
public interface PmsPortalBrandService {
    /**
     * 分页获取推荐品牌
     */
    List<PmsBrand> recommendList(Integer pageNum, Integer pageSize);

    /**
     * 获取品牌详情
     */
    PmsBrand detail(Long brandId);

    /**
     * 分页获取品牌相关商品
     */
    CommonPage<PmsProduct> productList(Long brandId, Integer pageNum, Integer pageSize);
}
