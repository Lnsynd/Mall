package com.lqs.mall.service.impl;

import com.lqs.mall.mapper.UmsResourceCategoryMapper;
import com.lqs.mall.model.UmsResourceCategory;
import com.lqs.mall.model.UmsResourceCategoryExample;
import com.lqs.mall.service.UmsResourceCategoryService;
import com.lqs.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 后台资源分类 实现类
 * Created by 刘千山 on 2023/7/2/002-17:33
 */
@Service
public class UmsResourceCategoryServiceImpl implements UmsResourceCategoryService {

    @Autowired
    private UmsResourceCategoryMapper resourceCategoryMapper;

    @Override
    public List<UmsResourceCategory> listAll() {
        UmsResourceCategoryExample example = new UmsResourceCategoryExample();
        example.setOrderByClause("sort desc");
        return resourceCategoryMapper.selectByExample(example);
    }

    @Override
    public int create(UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setCreateTime(new Date());
        return resourceCategoryMapper.insert(umsResourceCategory);
    }

    @Override
    public int update(Long id, UmsResourceCategory umsResourceCategory) {
        umsResourceCategory.setId(id);
        return resourceCategoryMapper.updateByPrimaryKeySelective(umsResourceCategory);
    }

    @Override
    public int delete(Long id) {
        return resourceCategoryMapper.deleteByPrimaryKey(id);
    }

}
