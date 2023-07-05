package com.lqs.mall.service;

import com.lqs.mall.model.CmsPrefrenceArea;

import java.util.List;

/**
 * 商品优选管理Service
 * Created by 刘千山 on 2023/7/5/005-18:51
 */
public interface CmsPrefrenceAreaService {

    /**
     * 获取所有商品优选
     */
    List<CmsPrefrenceArea> listAll();
}
