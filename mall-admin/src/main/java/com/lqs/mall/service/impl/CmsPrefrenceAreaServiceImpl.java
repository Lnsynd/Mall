package com.lqs.mall.service.impl;

import com.lqs.mall.mapper.CmsPrefrenceAreaMapper;
import com.lqs.mall.model.CmsPrefrenceArea;
import com.lqs.mall.model.CmsPrefrenceAreaExample;
import com.lqs.mall.service.CmsPrefrenceAreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品优选管理实现类
 * Created by 刘千山 on 2023/7/5/005-18:51
 */
@Service
public class CmsPrefrenceAreaServiceImpl implements CmsPrefrenceAreaService {
    @Autowired
    private CmsPrefrenceAreaMapper prefrenceAreaMapper;

    @Override
    public List<CmsPrefrenceArea> listAll() {
        return prefrenceAreaMapper.selectByExample(new CmsPrefrenceAreaExample());
    }
}
