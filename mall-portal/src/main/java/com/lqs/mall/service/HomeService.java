package com.lqs.mall.service;

import com.lqs.mall.domain.HomeContentResult;
import com.lqs.mall.model.CmsSubject;
import com.lqs.mall.model.PmsProduct;
import com.lqs.mall.model.PmsProductCategory;

import java.util.List;

/**
 * 首页内容管理Service
 * Created by 刘千山 on 2023/7/5/005-10:10
 */
public interface HomeService {
    /**
     * 首页内容
     */
    HomeContentResult content();

    /**
     * 分页获取推荐商品
     */
    List<PmsProduct> recommendProductList(Integer pageSize, Integer pageNum);

    /**
     * 获取首页商品
     * @param parentId 0:获取一级分类；其他：获取指定二级分类
     */
    List<PmsProductCategory> getProductCateList(Long parentId);

    /**
     * 根据分类获取专题
     * @param cateId 专题分类id
     */
    List<CmsSubject> getSubjectList(Long cateId, Integer pageSize, Integer pageNum);

    /**
     * 分页获取人气推荐商品
     */
    List<PmsProduct> hotProductList(Integer pageNum, Integer pageSize);

    List<PmsProduct> newProductList(Integer pageNum, Integer pageSize);
}
