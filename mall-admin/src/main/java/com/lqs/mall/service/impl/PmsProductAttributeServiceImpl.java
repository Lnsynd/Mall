package com.lqs.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.lqs.mall.dao.PmsProductAttributeDao;
import com.lqs.mall.dto.PmsProductAttributeParam;
import com.lqs.mall.dto.ProductAttrInfo;
import com.lqs.mall.mapper.PmsProductAttributeCategoryMapper;
import com.lqs.mall.mapper.PmsProductAttributeMapper;
import com.lqs.mall.model.PmsProductAttribute;
import com.lqs.mall.model.PmsProductAttributeCategory;
import com.lqs.mall.model.PmsProductAttributeExample;
import com.lqs.mall.service.PmsProductAttributeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品属性管理实现类
 * Created by 刘千山 on 2023/7/3/003-19:40
 */
@Service
public class PmsProductAttributeServiceImpl implements PmsProductAttributeService {
    @Autowired
    private PmsProductAttributeDao productAttributeDao;

    @Autowired
    private PmsProductAttributeMapper productAttributeMapper;

    @Autowired
    private PmsProductAttributeCategoryMapper productAttributeCategoryMapper;

    @Override
    public List<PmsProductAttribute> getList(Long cid, Integer type, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.setOrderByClause("sort desc");
        example.createCriteria().andProductAttributeCategoryIdEqualTo(cid).andTypeEqualTo(type);
        return productAttributeMapper.selectByExample(example);
    }

    @Override
    public int create(PmsProductAttributeParam productAttributeParam) {
        PmsProductAttribute pmsProductAttribute = new PmsProductAttribute();
        BeanUtils.copyProperties(productAttributeParam, pmsProductAttribute);
        int count = productAttributeMapper.insertSelective(pmsProductAttribute);
        // 新增商品属性以后  要更新商品属性分类数量
        // 获取当前属性  处于 哪个分类下
        PmsProductAttributeCategory productAttributeCategory = productAttributeCategoryMapper.selectByPrimaryKey(productAttributeParam.getProductAttributeCategoryId());
        // 如果当前属性是属于 规格
        if (pmsProductAttribute.getType() == 0) {
            productAttributeCategory.setAttributeCount(productAttributeCategory.getAttributeCount() + 1);
        } else if (pmsProductAttribute.getType() == 1) {
            // 如果当前属性属于 参数
            productAttributeCategory.setParamCount(productAttributeCategory.getParamCount() + 1);
        }
        return count;
    }

    @Override
    public int update(Long id, PmsProductAttributeParam productAttributeParam) {
        PmsProductAttribute attribute = new PmsProductAttribute();
        attribute.setId(id);
        BeanUtils.copyProperties(productAttributeParam, attribute);
        return productAttributeMapper.updateByPrimaryKeySelective(attribute);
    }

    @Override
    public PmsProductAttribute getItem(Long id) {
        return productAttributeMapper.selectByPrimaryKey(id);
    }

    @Override
    public int delete(List<Long> ids) {
        // 获取当前的id是哪个分类的
        PmsProductAttribute attribute = productAttributeMapper.selectByPrimaryKey(ids.get(0));
        PmsProductAttributeCategory productAttributeCategory = productAttributeCategoryMapper.selectByPrimaryKey(attribute.getProductAttributeCategoryId());

        // 删除存在 属性表中 的 ids 数据
        PmsProductAttributeExample example = new PmsProductAttributeExample();
        example.createCriteria().andIdIn(ids);
        int count = productAttributeMapper.deleteByExample(example);

        // 删除完成后修改 属性分类表中  规格或参数 数量
        Integer type = attribute.getType();
        // 如果属性是 规格 类的
        if (type == 0) {
            // 如果该分类下规格的  数量 >= 删除的数量,设置新的属性数量值
            if (productAttributeCategory.getAttributeCount() >= count) {
                productAttributeCategory.setAttributeCount(productAttributeCategory.getAttributeCount() - count);
            } else {
                productAttributeCategory.setAttributeCount(0);
            }
        } else if (type == 1) {
            // 如果属性是 参数 类的
            if (productAttributeCategory.getParamCount() >= count) {
                productAttributeCategory.setParamCount(productAttributeCategory.getParamCount() - count);
            } else {
                productAttributeCategory.setParamCount(0);
            }
        }
        productAttributeCategoryMapper.updateByPrimaryKey(productAttributeCategory);
        return count;

    }

    @Override
    public List<ProductAttrInfo> getProductAttrInfo(Long productCategoryId) {
        return productAttributeDao.getProductAttrInfo(productCategoryId);
    }
}
