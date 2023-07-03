package com.lqs.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.lqs.mall.dao.PmsSkuStockDao;
import com.lqs.mall.mapper.PmsSkuStockMapper;
import com.lqs.mall.model.PmsSkuStock;
import com.lqs.mall.model.PmsSkuStockExample;
import com.lqs.mall.service.PmsSkuStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * sku商品库存管理实现类
 * Created by 刘千山 on 2023/7/3/003-18:55
 */
@Service
public class PmsSkuStockServiceImpl implements PmsSkuStockService {

    @Autowired
    private PmsSkuStockMapper skuStockMapper;

    @Autowired
    private PmsSkuStockDao skuStockDao;

    @Override
    public List<PmsSkuStock> getList(Long pid, String keyword) {
        PmsSkuStockExample example = new PmsSkuStockExample();
        PmsSkuStockExample.Criteria criteria = example.createCriteria().andProductIdEqualTo(pid);
        if (StrUtil.isNotEmpty(keyword)) {
            criteria.andSkuCodeEqualTo("%" + keyword + "%");
        }
        return skuStockMapper.selectByExample(example);
    }

    @Override
    public int update(Long pid, List<PmsSkuStock> skuStockList) {
        return skuStockDao.replaceList(skuStockList);
    }
}
