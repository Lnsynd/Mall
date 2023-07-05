package com.lqs.mall.service;

import com.lqs.mall.domain.EsProduct;
import com.lqs.mall.domain.EsProductRelatedInfo;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * ES搜索商品管理Service
 * Created by 刘千山 on 2023/7/5/005-15:07
 */
public interface EsProductService {

    /**
     * 导入所有数据库商品到ES
     */
    int importAll();

    /**
     * 根据id在ES中删除商品
     */
    void delete(Long id);

    /**
     * 批量删除商品
     */
    void delete(List<Long> ids);

    /**
     * 根据商品id创建商品
     */
    EsProduct create(Long id);

    /**
     * 简单搜索
     * @param keyword 关键词
     */
    Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize);

    /**
     * 根据关键字搜索名称或者副标题复合查询
     */
    Page<EsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort);

    /**
     * 根据商品id推荐相关商品
     */
    Page<EsProduct> recommend(Long id, Integer pageNum, Integer pageSize);

    /**
     * 获取搜索词相关品牌、分类、属性
     * @param keyword 关键词
     */
    EsProductRelatedInfo searchRelatedInfo(String keyword);
}
