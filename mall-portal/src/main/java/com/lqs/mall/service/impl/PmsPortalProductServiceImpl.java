package com.lqs.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.lqs.mall.dao.PortalProductDao;
import com.lqs.mall.domain.PmsPortalProductDetail;
import com.lqs.mall.domain.PmsProductCategoryNode;
import com.lqs.mall.mapper.*;
import com.lqs.mall.model.*;
import com.lqs.mall.service.PmsPortalProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 前台商品管理
 * Created by 刘千山 on 2023/7/4/004-09:16
 */
@Service
public class PmsPortalProductServiceImpl implements PmsPortalProductService {
    @Autowired
    private PmsProductLadderMapper productLadderMapper;
    @Autowired
    private PmsProductFullReductionMapper productFullReductionMapper;

    @Autowired
    private PmsProductAttributeValueMapper productAttributeValueMapper;
    @Autowired
    private PmsProductAttributeMapper productAttributeMapper;
    @Autowired
    private PmsProductCategoryMapper productCategoryMapper;
    @Autowired
    private PmsSkuStockMapper skuStockMapper;

    @Autowired
    private PmsProductMapper productMapper;

    @Autowired
    private PmsBrandMapper brandMapper;

    @Autowired
    private PortalProductDao portalProductDao;

    @Override
    public List<PmsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample example = new PmsProductExample();
        PmsProductExample.Criteria criteria = example.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        criteria.andPublishStatusEqualTo(1);
        if (StrUtil.isNotEmpty(keyword)) {
            criteria.andNameLike("%" + keyword + "%");
        }
        if (brandId != null) {
            criteria.andBrandIdEqualTo(brandId);
        }
        if (productCategoryId != null) {
            criteria.andProductCategoryIdEqualTo(productCategoryId);
        }
        //1->按新品；2->按销量；3->价格从低到高；4->价格从高到低
        if (sort == 1) {
            example.setOrderByClause("id desc");
        } else if (sort == 2) {
            example.setOrderByClause("sale desc");
        } else if (sort == 3) {
            example.setOrderByClause("price asc");
        } else if (sort == 4) {
            example.setOrderByClause("price desc");
        }
        return productMapper.selectByExample(example);
    }

    @Override
    public List<PmsProductCategoryNode> categoryTreeList() {
        // 查询所有的分类
        List<PmsProductCategory> productCategories = productCategoryMapper.selectByExample(new PmsProductCategoryExample());
        List<PmsProductCategoryNode> result = productCategories.stream()
                .filter(item -> item.getParentId().equals(0L))
                .map(item -> convert(item, productCategories)).collect(Collectors.toList());
        return result;
    }

    @Override
    public PmsPortalProductDetail detail(Long id) {
        PmsPortalProductDetail res = new PmsPortalProductDetail();
        // 获取商品信息
        PmsProduct product = productMapper.selectByPrimaryKey(id);
        res.setProduct(product);
        // 获取品牌信息
        PmsBrand brand = brandMapper.selectByPrimaryKey(product.getBrandId());
        res.setBrand(brand);
        //获取商品属性信息
        PmsProductAttributeExample attributeExample = new PmsProductAttributeExample();
        attributeExample.createCriteria().andProductAttributeCategoryIdEqualTo(product.getProductAttributeCategoryId());
        List<PmsProductAttribute> productAttributeList = productAttributeMapper.selectByExample(attributeExample);
        res.setProductAttributeList(productAttributeList);
        // 获取商品属性值信息
        // 获取全部属性id
        List<Long> attrIds = productAttributeList.stream().map(PmsProductAttribute::getId).collect(Collectors.toList());
        PmsProductAttributeValueExample attributeValueExample = new PmsProductAttributeValueExample();
        attributeValueExample.createCriteria().andProductIdEqualTo(product.getId())
                .andProductAttributeIdIn(attrIds);
        List<PmsProductAttributeValue> productAttributeValueList = productAttributeValueMapper.selectByExample(attributeValueExample);
        res.setProductAttributeValueList(productAttributeValueList);
        //获取商品SKU库存信息
        PmsSkuStockExample skuExample = new PmsSkuStockExample();
        skuExample.createCriteria().andProductIdEqualTo(product.getId());
        List<PmsSkuStock> skuStockList = skuStockMapper.selectByExample(skuExample);
        res.setSkuStockList(skuStockList);
        //商品阶梯价格设置
        if(product.getPromotionType()==3){
            PmsProductLadderExample ladderExample = new PmsProductLadderExample();
            ladderExample.createCriteria().andProductIdEqualTo(product.getId());
            List<PmsProductLadder> productLadderList = productLadderMapper.selectByExample(ladderExample);
            res.setProductLadderList(productLadderList);
        }
        //商品满减价格设置
        if(product.getPromotionType()==4){
            PmsProductFullReductionExample fullReductionExample = new PmsProductFullReductionExample();
            fullReductionExample.createCriteria().andProductIdEqualTo(product.getId());
            List<PmsProductFullReduction> productFullReductionList = productFullReductionMapper.selectByExample(fullReductionExample);
            res.setProductFullReductionList(productFullReductionList);
        }
        //商品可用优惠券
        res.setCouponList(portalProductDao.getAvailableCouponList(product.getId(),product.getProductCategoryId()));
        return res;
    }

    /**
     * 初始对象转换为节点对象
     */
    private PmsProductCategoryNode convert(PmsProductCategory item, List<PmsProductCategory> productCategories) {
        PmsProductCategoryNode node = new PmsProductCategoryNode();
        BeanUtils.copyProperties(item, node);
        // 查询子节点
        List<PmsProductCategoryNode> children = productCategories.stream()
                .filter(subItem -> subItem.getParentId().equals(item.getId()))
                .map(subItem -> convert(subItem, productCategories)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
}
