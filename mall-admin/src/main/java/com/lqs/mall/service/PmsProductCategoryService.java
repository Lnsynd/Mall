package com.lqs.mall.service;

import com.lqs.mall.dto.PmsProductCategoryParam;
import com.lqs.mall.dto.PmsProductCategoryWithChildrenItem;
import com.lqs.mall.model.PmsProductCategory;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 后台-商品分类管理Service
 * Created by 刘千山 on 2023/7/3/003-14:40
 */
public interface PmsProductCategoryService {
    /**
     * 添加商品分类
     */
    @Transactional
    int create(PmsProductCategoryParam productCategoryParam);

    /**
     * 修改商品分类
     */
    @Transactional
    int update(Long id, PmsProductCategoryParam productCategoryParam);

    /**
     * 分页查询商品分类
     */
    List<PmsProductCategory> getList(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 根据id获取分类
     */
    PmsProductCategory getItem(Long id);

    /**
     * 根据id删除分类
     */
    int delete(Long id);

    /**
     * 修改导航栏显示状态
     */
    int updateNavStatus(List<Long> ids, Integer navStatus);

    /**
     * 修改显示状态
     */
    int updateShowStatus(List<Long> ids, Integer showStatus);

    /**
     * 以层级形式获取商品分类
     */
    List<PmsProductCategoryWithChildrenItem> listWithChildren();
}
