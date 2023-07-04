package com.lqs.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.lqs.mall.common.api.CommonPage;
import com.lqs.mall.dao.HomeDao;
import com.lqs.mall.mapper.PmsBrandMapper;
import com.lqs.mall.mapper.PmsProductMapper;
import com.lqs.mall.model.PmsBrand;
import com.lqs.mall.model.PmsProduct;
import com.lqs.mall.model.PmsProductExample;
import com.lqs.mall.service.PmsPortalBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 首页品牌推荐管理
 * Created by 刘千山 on 2023/7/4/004-09:03
 */
@Service
public class PmsPortalBrandServiceImpl implements PmsPortalBrandService {

    @Autowired
    private HomeDao homeDao;
    @Autowired
    private PmsBrandMapper brandMapper;
    @Autowired
    private PmsProductMapper productMapper;

    @Override
    public List<PmsBrand> recommendList(Integer pageNum, Integer pageSize) {
        int offset = (pageNum - 1) * pageSize;
        return homeDao.getRecommendBrandList(offset, pageSize);
    }

    @Override
    public PmsBrand detail(Long brandId) {
        return brandMapper.selectByPrimaryKey(brandId);
    }

    @Override
    public CommonPage<PmsProduct> productList(Long brandId, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andDeleteStatusEqualTo(0)
                .andPublishStatusEqualTo(1)
                .andBrandIdEqualTo(brandId);
        List<PmsProduct> productList = productMapper.selectByExample(example);
        return CommonPage.restPage(productList);
    }
}
