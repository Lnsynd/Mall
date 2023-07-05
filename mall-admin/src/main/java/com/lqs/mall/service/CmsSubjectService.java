package com.lqs.mall.service;

import com.lqs.mall.model.CmsSubject;

import java.util.List;

/**
 * 商品专题管理Service
 * Created by 刘千山 on 2023/7/5/005-18:53
 */
public interface CmsSubjectService {
    /**
     * 获取全部商品专题
     */
    List<CmsSubject> listAll();

    /**
     * 根据关键字 分页获取商品专题
     */
    List<CmsSubject> list(String keyword, Integer pageNum, Integer pageSize);
}
