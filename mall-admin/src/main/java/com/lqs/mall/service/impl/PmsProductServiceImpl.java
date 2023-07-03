package com.lqs.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.lqs.mall.dao.*;
import com.lqs.mall.dto.PmsProductParam;
import com.lqs.mall.dao.PmsProductAttributeValueDao;
import com.lqs.mall.dto.PmsProductQueryParam;
import com.lqs.mall.dto.PmsProductResult;
import com.lqs.mall.mapper.*;
import com.lqs.mall.model.*;
import com.lqs.mall.service.PmsProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台-商品Service实现类
 * Created by 刘千山 on 2023/7/3/003-15:53
 */
@Service
public class PmsProductServiceImpl implements PmsProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PmsProductServiceImpl.class);

    @Autowired
    private PmsProductService productService;
    @Autowired
    private PmsProductFullReductionDao productFullReductionDao;
    @Autowired
    private PmsProductFullReductionMapper productFullReductionMapper;
    @Autowired
    private PmsMemberPriceDao memberPriceDao;
    @Autowired
    private PmsMemberPriceMapper memberPriceMapper;
    @Autowired
    private PmsProductLadderDao productLadderDao;
    @Autowired
    private PmsProductLadderMapper productLadderMapper;
    @Autowired
    private PmsSkuStockDao skuStockDao;
    @Autowired
    private PmsSkuStockMapper skuStockMapper;
    @Autowired
    private PmsProductAttributeValueDao productAttributeValueDao;
    @Autowired
    private PmsProductAttributeValueMapper productAttributeValueMapper;
    @Autowired
    private CmsSubjectProductRelationDao subjectProductRelationDao;
    @Autowired
    private CmsSubjectProductRelationMapper subjectProductRelationMapper;
    @Autowired
    private CmsPrefrenceAreaProductRelationDao prefrenceAreaProductRelationDao;
    @Autowired
    private CmsPrefrenceAreaProductRelationMapper prefrenceAreaProductRelationMapper;
    @Autowired
    private PmsProductDao productDao;
    @Autowired
    private PmsProductMapper productMapper;

    @Autowired
    private PmsProductVertifyRecordDao productVertifyRecordDao;

    @Override
    public int create(PmsProductParam productParam) {
        int count;
        // 创建商品
        PmsProduct product = new PmsProduct();
        product.setId(null);
        productMapper.insertSelective(product);
        //根据促销类型设置价格：会员价格、阶梯价格、满减价格
        Long productId = product.getId();
        //会员价格
        relateAndInsertList(memberPriceDao, productParam.getMemberPriceList(), productId);
        //阶梯价格
        relateAndInsertList(productLadderDao, productParam.getProductLadderList(), productId);
        //满减价格
        relateAndInsertList(productFullReductionDao, productParam.getProductFullReductionList(), productId);
        //处理sku的编码
        handleSkuStockCode(productParam.getSkuStockList(), productId);
        //添加sku库存信息
        relateAndInsertList(skuStockDao, productParam.getSkuStockList(), productId);
        //添加商品参数,添加自定义商品规格
        relateAndInsertList(productAttributeValueDao, productParam.getProductAttributeValueList(), productId);
        //关联专题
        relateAndInsertList(subjectProductRelationDao, productParam.getSubjectProductRelationList(), productId);
        //关联优选
        relateAndInsertList(prefrenceAreaProductRelationDao, productParam.getPrefrenceAreaProductRelationList(), productId);
        count = 1;
        return count;
    }

    @Override
    public PmsProductResult getUpdateInfo(Long id) {
        return productDao.getUpdateInfo(id);
    }

    @Override
    public int update(Long id, PmsProductParam productParam) {
        int count;
        // 更新商品信息
        PmsProduct product = new PmsProduct();
        product.setId(id);
        productMapper.updateByPrimaryKeySelective(product);
        // 会员价格
        PmsMemberPriceExample pmsMemberPriceExample = new PmsMemberPriceExample();
        pmsMemberPriceExample.createCriteria().andProductIdEqualTo(id);
        memberPriceMapper.deleteByExample(pmsMemberPriceExample);
        relateAndInsertList(memberPriceDao, productParam.getMemberPriceList(), id);
        //阶梯价格
        PmsProductLadderExample ladderExample = new PmsProductLadderExample();
        ladderExample.createCriteria().andProductIdEqualTo(id);
        productLadderMapper.deleteByExample(ladderExample);
        relateAndInsertList(productLadderDao, productParam.getProductLadderList(), id);
        //满减价格
        PmsProductFullReductionExample fullReductionExample = new PmsProductFullReductionExample();
        fullReductionExample.createCriteria().andProductIdEqualTo(id);
        productFullReductionMapper.deleteByExample(fullReductionExample);
        relateAndInsertList(productFullReductionDao, productParam.getProductFullReductionList(), id);
        //修改sku库存信息
        handleUpdateSkuStockList(id, productParam);
        //修改商品参数,添加自定义商品规格
        PmsProductAttributeValueExample productAttributeValueExample = new PmsProductAttributeValueExample();
        productAttributeValueExample.createCriteria().andProductIdEqualTo(id);
        productAttributeValueMapper.deleteByExample(productAttributeValueExample);
        relateAndInsertList(productAttributeValueDao, productParam.getProductAttributeValueList(), id);
        //关联专题
        CmsSubjectProductRelationExample subjectProductRelationExample = new CmsSubjectProductRelationExample();
        subjectProductRelationExample.createCriteria().andProductIdEqualTo(id);
        subjectProductRelationMapper.deleteByExample(subjectProductRelationExample);
        relateAndInsertList(subjectProductRelationDao, productParam.getSubjectProductRelationList(), id);
        //关联优选
        CmsPrefrenceAreaProductRelationExample prefrenceAreaExample = new CmsPrefrenceAreaProductRelationExample();
        prefrenceAreaExample.createCriteria().andProductIdEqualTo(id);
        prefrenceAreaProductRelationMapper.deleteByExample(prefrenceAreaExample);
        relateAndInsertList(prefrenceAreaProductRelationDao, productParam.getPrefrenceAreaProductRelationList(), id);
        count = 1;
        return count;
    }



    /**
     * 更新sku编码
     *
     * @param id           商品id
     * @param productParam 要更新的参数
     */
    private void handleUpdateSkuStockList(Long id, PmsProductParam productParam) {
        // 获取当前的sku信息
        List<PmsSkuStock> currSkuList = productParam.getSkuStockList();
        // 如果没有sku信息，直接删除旧的信息
        if (CollUtil.isEmpty(currSkuList)) {
            PmsSkuStockExample example = new PmsSkuStockExample();
            example.createCriteria().andProductIdEqualTo(id);
            skuStockMapper.deleteByExample(example);
            return;
        }
        // 获取数据库中存在的 旧的 sku信息
        PmsSkuStockExample stockExample = new PmsSkuStockExample();
        stockExample.createCriteria().andProductIdEqualTo(id);
        List<PmsSkuStock> oriSkuStocks = skuStockMapper.selectByExample(stockExample);
        // 获取新增的sku信息(约定过 新增的sku id为空)
        List<PmsSkuStock> insertSkuList = currSkuList.stream().filter(item -> item.getId() == null).collect(Collectors.toList());
        // 获取要更新的sku信息(要更新的sku存在id)
        List<PmsSkuStock> updateSkuList = currSkuList.stream().filter(item -> item.getId() != null).collect(Collectors.toList());
        // 获取要更新的skuID列表
        List<Long> updateSkuIds = updateSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
        //   从旧的sku信息中过滤出 要更新的skuIds中 不存在的 信息 => 要删除的sku信息
        List<PmsSkuStock> deleteSkuList = oriSkuStocks.stream().filter(item -> !updateSkuIds.contains(item.getId())).collect(Collectors.toList());
        handleSkuStockCode(insertSkuList, id);
        handleSkuStockCode(updateSkuList, id);
        // 新增sku
        if (CollUtil.isNotEmpty(insertSkuList)) {
            relateAndInsertList(skuStockDao, insertSkuList, id);
        }
        // 删除sku
        if (CollUtil.isNotEmpty(deleteSkuList)) {
            List<Long> deleteSkuIds = deleteSkuList.stream().map(PmsSkuStock::getId).collect(Collectors.toList());
            PmsSkuStockExample removeExample = new PmsSkuStockExample();
            removeExample.createCriteria().andIdIn(deleteSkuIds);
            skuStockMapper.deleteByExample(removeExample);
        }
        //修改sku
        if (CollUtil.isNotEmpty(updateSkuList)) {
            for (PmsSkuStock pmsSkuStock : updateSkuList) {
                skuStockMapper.updateByPrimaryKeySelective(pmsSkuStock);
            }
        }
    }

    /**
     * 处理sku编码
     *
     * @param skuStockList sku列表
     * @param productId    商品id
     */
    private void handleSkuStockCode(List<PmsSkuStock> skuStockList, Long productId) {
        if (CollectionUtils.isEmpty(skuStockList)) return;
        for (int i = 0; i < skuStockList.size(); i++) {
            PmsSkuStock pmsSkuStock = skuStockList.get(i);
            if (StrUtil.isEmpty(pmsSkuStock.getSkuCode())) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                StringBuilder sb = new StringBuilder();
                // 添加日期
                sb.append(sdf.format(new Date()));
                // 添加 四位商品id
                sb.append(String.format("%04d", productId));
                // 添加 三位索引id
                sb.append(String.format("%03d", i + 1));
                pmsSkuStock.setSkuCode(sb.toString());
            }
        }
    }


    /**
     * 建立和插入关系表
     *
     * @param dao       可以操作的dao
     * @param dataList  要插入的数据
     * @param productId 建立关系的商品id
     */
    private void relateAndInsertList(Object dao, List dataList, Long productId) {
        try {
            // 如果数据为空则返回
            if (CollUtil.isEmpty(dataList)) {
                return;
            }
            for (Object item : dataList) {
                Method setId = item.getClass().getMethod("setId", Long.class);
                setId.invoke(item, (Long) null); // invoke就是 再次调用 setId方法
                Method setProductId = item.getClass().getMethod("setProductId", Long.class);
                setProductId.invoke(item, productId);
            }
            Method insertList = dao.getClass().getMethod("insertList", List.class);
            insertList.invoke(dao, dataList);
        } catch (Exception e) {
            LOGGER.warn("创建产品出错:{}", e.getMessage());
            throw new RuntimeException(e.getMessage());
        }

    }

    @Override
    public List<PmsProduct> list(PmsProductQueryParam productQueryParam, Integer pageSize, Integer pageNum) {
        PageHelper.startPage(pageNum, pageSize);
        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if(productQueryParam.getPublishStatus() !=null){
            criteria.andPublishStatusEqualTo(productQueryParam.getPublishStatus());
        }
        if (productQueryParam.getVerifyStatus() != null) {
            criteria.andVerifyStatusEqualTo(productQueryParam.getVerifyStatus());
        }
        if (!StrUtil.isEmpty(productQueryParam.getKeyword())) {
            criteria.andNameLike("%" + productQueryParam.getKeyword() + "%");
        }
        if (!StrUtil.isEmpty(productQueryParam.getProductSn())) {
            criteria.andProductSnEqualTo(productQueryParam.getProductSn());
        }
        if (productQueryParam.getBrandId() != null) {
            criteria.andBrandIdEqualTo(productQueryParam.getBrandId());
        }
        if (productQueryParam.getProductCategoryId() != null) {
            criteria.andProductCategoryIdEqualTo(productQueryParam.getProductCategoryId());
        }
        return productMapper.selectByExample(productExample);
    }

    @Override
    public List<PmsProduct> list(String keyword) {
        PmsProductExample productExample = new PmsProductExample();
        PmsProductExample.Criteria criteria = productExample.createCriteria();
        criteria.andDeleteStatusEqualTo(0);
        if(!StrUtil.isEmpty(keyword)){
            criteria.andNameLike("%" + keyword + "%");
            productExample.or().andDeleteStatusEqualTo(0).andProductSnLike("%" + keyword + "%");
        }
        return productMapper.selectByExample(productExample);
    }

    @Override
    public int updateVerifyStatus(List<Long> ids, Integer verifyStatus, String detail) {
        PmsProduct product = new PmsProduct();
        product.setVerifyStatus(verifyStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        int count = productMapper.updateByExampleSelective(product, example);
        // 新建一个审核记录列表
        List<PmsProductVertifyRecord> list = new ArrayList<>();
        // 插入审核记录
        for(Long id: ids){
            PmsProductVertifyRecord record = new PmsProductVertifyRecord();
            record.setProductId(id);
            record.setCreateTime(new Date());
            record.setDetail(detail);
            record.setStatus(verifyStatus);
            record.setVertifyMan("test");
            list.add(record);
        }
        productVertifyRecordDao.insertList(list);
        return count;
    }

    @Override
    public int updatePublishStatus(List<Long> ids, Integer publishStatus) {
        PmsProduct product =new PmsProduct();
        product.setPublishStatus(publishStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(product,example);
    }

    @Override
    public int updateRecommendStatus(List<Long> ids, Integer recommendStatus) {
        PmsProduct record = new PmsProduct();
        record.setRecommandStatus(recommendStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateNewStatus(List<Long> ids, Integer newStatus) {
        PmsProduct record = new PmsProduct();
        record.setNewStatus(newStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int updateDeleteStatus(List<Long> ids, Integer deleteStatus) {
        PmsProduct record = new PmsProduct();
        record.setDeleteStatus(deleteStatus);
        PmsProductExample example = new PmsProductExample();
        example.createCriteria().andIdIn(ids);
        return productMapper.updateByExampleSelective(record, example);
    }


}
