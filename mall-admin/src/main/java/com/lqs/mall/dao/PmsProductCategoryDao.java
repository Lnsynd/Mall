package com.lqs.mall.dao;

import com.lqs.mall.dto.PmsProductCategoryWithChildrenItem;

import java.util.List;

/**
 * 操作商品分类 自定义dao
 * Created by 刘千山 on 2023/7/3/003-15:30
 */
public interface PmsProductCategoryDao {
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
