package com.lqs.mall.service;

import com.lqs.mall.domain.PmsPortalProductDetail;
import com.lqs.mall.domain.PmsProductCategoryNode;
import com.lqs.mall.model.PmsProduct;

import java.util.List;

/**
 * 前台商品管理
 * Created by 刘千山 on 2023/7/4/004-09:16
 */
public interface PmsPortalProductService {
    /**
     * 前台页面搜索商品信息
     * @param keyword 关键字
     * @param brandId 品牌id
     * @param productCategoryId 商品分类id
     * @param pageNum 第几页
     * @param pageSize 每页大小
     * @param sort 排序方式
     */
    List<PmsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort);

    /**
     * 树形结构获取商品列表
     */
    List<PmsProductCategoryNode> categoryTreeList();

    /**
     * 前台获取商品详情
     */
    PmsPortalProductDetail detail(Long id);
}
