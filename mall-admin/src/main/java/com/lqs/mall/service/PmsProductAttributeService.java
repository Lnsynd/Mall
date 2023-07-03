package com.lqs.mall.service;

import com.lqs.mall.dto.PmsProductAttributeParam;
import com.lqs.mall.dto.ProductAttrInfo;
import com.lqs.mall.model.PmsProductAttribute;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商品属性管理Service
 * Created by 刘千山 on 2023/7/3/003-19:40
 */
public interface PmsProductAttributeService {

    /**
     * 分页查询属性列表
     * @param cid 属性分类的id
     * @param type 0->查询属性，1->查询参数
     */
    List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum);

    /**
     * 添加商品属性信息
     */
    @Transactional
    int create(PmsProductAttributeParam productAttributeParam);

    /**
     * 修改商品属性信息
     * @param id 商品属性id
     * @param productAttributeParam 要修改的商品属性参数
     */
    int update(Long id, PmsProductAttributeParam productAttributeParam);

    /**
     * 获取单个属性信息
     */
    PmsProductAttribute getItem(Long id);

    /**
     * 批量删除商品属性
     * @param ids 要删除的属性id
     */
    @Transactional
    int delete(List<Long> ids);


    /**
     * 根据商品分类的id获取商品属性及属性分类
     * @param productCategoryId 产品的分类ID
     */
    List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId);
}
