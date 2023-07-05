package com.lqs.mall.service;

import com.lqs.mall.model.UmsMemberLevel;

import java.util.List;

/**
 * 会员等级管理
 * Created by 刘千山 on 2023/7/5/005-18:04
 */
public interface UmsMemberLevelService {

    /**
     * 查询全部会员等级
     * @param defaultStatus 是否为默认会员
     */
    List<UmsMemberLevel> list(Integer defaultStatus);
}
