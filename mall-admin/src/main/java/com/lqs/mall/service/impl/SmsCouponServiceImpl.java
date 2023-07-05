package com.lqs.mall.service.impl;

import cn.hutool.core.util.StrUtil;
import com.github.pagehelper.PageHelper;
import com.lqs.mall.dao.SmsCouponDao;
import com.lqs.mall.dao.SmsCouponProductCategoryRelationDao;
import com.lqs.mall.dao.SmsCouponProductRelationDao;
import com.lqs.mall.dto.SmsCouponParam;
import com.lqs.mall.mapper.SmsCouponMapper;
import com.lqs.mall.mapper.SmsCouponProductCategoryRelationMapper;
import com.lqs.mall.mapper.SmsCouponProductRelationMapper;
import com.lqs.mall.model.*;
import com.lqs.mall.service.SmsCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 优惠券管理Service实现类
 * Created by 刘千山 on 2023/7/5/005-08:35
 */
@Service
public class SmsCouponServiceImpl implements SmsCouponService {

    @Autowired
    private SmsCouponProductCategoryRelationMapper couponProductCategoryRelationMapper;

    @Autowired
    private SmsCouponProductRelationMapper couponProductRelationMapper;

    @Autowired
    private SmsCouponProductRelationDao couponProductRelationDao;

    @Autowired
    private SmsCouponProductCategoryRelationDao couponProductCategoryRelationDao;

    @Autowired
    private SmsCouponMapper couponMapper;

    @Autowired
    private SmsCouponDao couponDao;

    @Override
    public int create(SmsCouponParam couponParam) {
        // 设置优惠券数量
        couponParam.setCount(couponParam.getPublishCount());
        // 设置已使用数量
        couponParam.setUseCount(0);
        // 设置已领取数量
        couponParam.setReceiveCount(0);
        // 插入优惠券表
        int count = couponMapper.insert(couponParam);
        // 插入 优惠券 和 商品 关系表
        if (couponParam.getUseType().equals(2)) {
            for (SmsCouponProductRelation couponProductRelation : couponParam.getProductRelationList()) {
                couponProductRelation.setCouponId(couponParam.getId());
            }
            couponProductRelationDao.insertList(couponParam.getProductRelationList());
        }
        // 插入 优惠券 和 商品分类 关系表
        if(couponParam.getUseType().equals(1)){
            for (SmsCouponProductCategoryRelation couponProductCategoryRelation : couponParam.getProductCategoryRelationList()) {
                couponProductCategoryRelation.setCouponId(couponParam.getId());
            }
            couponProductCategoryRelationDao.insertList(couponParam.getProductCategoryRelationList());
        }
        return count;
    }

    @Override
    public int delete(Long id) {
        //删除优惠券
        int count = couponMapper.deleteByPrimaryKey(id);
        //删除商品关联
        deleteProductRelation(id);
        //删除商品分类关联
        deleteProductCategoryRelation(id);
        return count;
    }

    /**
     * 删除 优惠券 与 商品分类 关联
     */
    private void deleteProductCategoryRelation(Long id) {
        SmsCouponProductCategoryRelationExample productCategoryRelationExample = new SmsCouponProductCategoryRelationExample();
        productCategoryRelationExample.createCriteria().andCouponIdEqualTo(id);
        couponProductCategoryRelationMapper.deleteByExample(productCategoryRelationExample);
    }

    /**
     * 删除 优惠券 与 商品 关联
     */
    private void deleteProductRelation(Long id) {
        SmsCouponProductRelationExample productRelationExample = new SmsCouponProductRelationExample();
        productRelationExample.createCriteria().andCouponIdEqualTo(id);
        couponProductRelationMapper.deleteByExample(productRelationExample);
    }

    @Override
    public int update(Long id, SmsCouponParam couponParam) {
        couponParam.setId(id);
        int count =couponMapper.updateByPrimaryKey(couponParam);
        //删除后插入优惠券和商品关系表
        if(couponParam.getUseType().equals(2)){
            for(SmsCouponProductRelation productRelation:couponParam.getProductRelationList()){
                productRelation.setCouponId(couponParam.getId());
            }
            deleteProductRelation(id);
            couponProductRelationDao.insertList(couponParam.getProductRelationList());
        }
        //删除后插入优惠券和商品分类关系表
        if(couponParam.getUseType().equals(1)){
            for (SmsCouponProductCategoryRelation couponProductCategoryRelation : couponParam.getProductCategoryRelationList()) {
                couponProductCategoryRelation.setCouponId(couponParam.getId());
            }
            deleteProductCategoryRelation(id);
            couponProductCategoryRelationDao.insertList(couponParam.getProductCategoryRelationList());
        }
        return count;
    }

    @Override
    public List<SmsCoupon> list(String name, Integer type, Integer pageSize, Integer pageNum) {
        SmsCouponExample example = new SmsCouponExample();
        SmsCouponExample.Criteria criteria = example.createCriteria();
        if(!StrUtil.isEmpty(name)){
            criteria.andNameLike("%"+name+"%");
        }
        if(type!=null){
            criteria.andTypeEqualTo(type);
        }
        PageHelper.startPage(pageNum,pageSize);
        return couponMapper.selectByExample(example);
    }

    @Override
    public SmsCouponParam getItem(Long id) {
        return couponDao.getItem(id);
    }

}
