package com.lqs.mall.service;

import com.lqs.mall.dto.PmsProductAttributeCategoryItem;
import com.lqs.mall.model.PmsProductAttributeCategory;

import java.util.List;

/**
 * 商品属性 分类管理
 * Created by 刘千山 on 2023/7/3/003-19:12
 */
public interface PmsProductAttributeCategoryService {
    /**
     * 添加商品属性分类
     */
    int create(String name);

    /**
     * 修改商品属性分类
     * @param id 商品id
     * @param name 商品名称
     */
    int update(Long id, String name);

    /**
     * 删除商品属性 分类
     * @param id 分类的id
     */
    int delete(Long id);

    /**
     * 获取单个商品属性分类
     */
    PmsProductAttributeCategory getItem(Long id);

    /**
     * 分页获取商品属性分类
     */
    List<PmsProductAttributeCategory> getList(Integer pageSize, Integer pageNum);

    /**
     * 获取包含属性的属性分类
     */
    List<PmsProductAttributeCategoryItem> getListWithAttr();
}
