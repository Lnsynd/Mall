package com.lqs.mall.service;

import com.lqs.mall.model.UmsResourceCategory;

import java.util.List;

/**
 * 后台资源分类
 * Created by 刘千山 on 2023/7/2/002-17:32
 */
public interface UmsResourceCategoryService {

    /**
     * 获取全部资源分类
     */
    List<UmsResourceCategory> listAll();

    /**
     * 添加资源分类
     */
    int create(UmsResourceCategory umsResourceCategory);

    /**
     * 修改资源分类
     */
    int update(Long id, UmsResourceCategory umsResourceCategory);

    /**
     * 删除资源分类
     */
    int delete(Long id);
}
