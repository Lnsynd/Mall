package com.lqs.mall.service.impl;

import com.lqs.mall.mapper.UmsMemberLevelMapper;
import com.lqs.mall.model.UmsMemberLevel;
import com.lqs.mall.model.UmsMemberLevelExample;
import com.lqs.mall.service.UmsMemberLevelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 会员等级管理实现类
 * Created by 刘千山 on 2023/7/5/005-18:04
 */
@Service
public class UmsMemberLevelServiceImpl implements UmsMemberLevelService {
    @Autowired
    private UmsMemberLevelMapper memberLevelMapper;
    @Override
    public List<UmsMemberLevel> list(Integer defaultStatus) {
        UmsMemberLevelExample example = new UmsMemberLevelExample();
        example.createCriteria().andDefaultStatusEqualTo(defaultStatus);
        return memberLevelMapper.selectByExample(example);
    }
}
