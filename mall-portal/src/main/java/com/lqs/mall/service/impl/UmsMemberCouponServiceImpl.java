package com.lqs.mall.service.impl;

import com.lqs.mall.dao.SmsCouponHistoryDao;
import com.lqs.mall.domain.CartPromotionItem;
import com.lqs.mall.domain.SmsCouponHistoryDetail;
import com.lqs.mall.model.SmsCouponProductCategoryRelation;
import com.lqs.mall.model.SmsCouponProductRelation;
import com.lqs.mall.model.UmsMember;
import com.lqs.mall.service.UmsMemberCouponService;
import com.lqs.mall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 用户优惠券管理Service实现类
 * Created by 刘千山 on 2023/7/4/004-17:51
 */
@Service
public class UmsMemberCouponServiceImpl implements UmsMemberCouponService {

    @Autowired
    private UmsMemberService memberService;

    @Autowired
    private SmsCouponHistoryDao couponHistoryDao;
    @Override
    public List<SmsCouponHistoryDetail> listCart(List<CartPromotionItem> cartPromotionItemList, Integer type) {
        // 获取当前用户
        UmsMember currentMember = memberService.getCurrentMember();
        Date now = new Date();
        //获取该用户所有优惠券
        List<SmsCouponHistoryDetail> allList = couponHistoryDao.getDetailList(currentMember.getId());
        //根据优惠券使用类型来判断优惠券是否可用
        List<SmsCouponHistoryDetail> enableList = new ArrayList<>();
        List<SmsCouponHistoryDetail> disableList = new ArrayList<>();
        // 遍历领取过的优惠券
        for(SmsCouponHistoryDetail couponHistoryDetail:allList){
            // 获取使用类型
            Integer useType = couponHistoryDetail.getCoupon().getUseType();
            // 是否无门槛
            BigDecimal minPoint = couponHistoryDetail.getCoupon().getMinPoint();
            Date endTime = couponHistoryDetail.getCoupon().getEndTime();
            if(useType.equals(0)){
                //0->全场通用
                //判断是否满足优惠起点
                //计算购物车商品的总价
                BigDecimal totalAmount = calcTotalAmount(cartPromotionItemList);
                if(now.before(endTime)&&totalAmount.subtract(minPoint).intValue()>=0){
                    enableList.add(couponHistoryDetail);
                }else{
                    disableList.add(couponHistoryDetail);
                }
            }else if(useType.equals(1)){
                //1->指定分类
                //计算指定分类商品的总价
                List<Long> productCategoryIds = new ArrayList<>();
                for (SmsCouponProductCategoryRelation categoryRelation : couponHistoryDetail.getCategoryRelationList()) {
                    productCategoryIds.add(categoryRelation.getProductCategoryId());
                }
                BigDecimal totalAmount = calcTotalAmountByproductCategoryId(cartPromotionItemList,productCategoryIds);
                if(now.before(endTime)&&totalAmount.intValue()>0&&totalAmount.subtract(minPoint).intValue()>=0){
                    enableList.add(couponHistoryDetail);
                }else{
                    disableList.add(couponHistoryDetail);
                }
            }else if(useType.equals(2)){
                //2->指定商品
                //计算指定商品的总价
                List<Long> productIds = new ArrayList<>();
                for (SmsCouponProductRelation productRelation : couponHistoryDetail.getProductRelationList()) {
                    productIds.add(productRelation.getProductId());
                }
                BigDecimal totalAmount = calcTotalAmountByProductId(cartPromotionItemList,productIds);
                if(now.before(endTime)&&totalAmount.intValue()>0&&totalAmount.subtract(minPoint).intValue()>=0){
                    enableList.add(couponHistoryDetail);
                }else{
                    disableList.add(couponHistoryDetail);
                }
            }
        }
        if(type.equals(1)){
            return enableList;
        }else{
            return disableList;
        }
    }

    /**
     * 通过商品ID计算总价
     */
    private BigDecimal calcTotalAmountByProductId(List<CartPromotionItem> cartPromotionItemList, List<Long> productIds) {
        BigDecimal total = new BigDecimal("0");
        for (CartPromotionItem item : cartPromotionItemList) {
            if(productIds.contains(item.getProductId())){
                BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
                total=total.add(realPrice.multiply(new BigDecimal(item.getQuantity())));
            }
        }
        return total;
    }

    /**
     * 通过商品分类计算购物车总价
     */
    private BigDecimal calcTotalAmountByproductCategoryId(List<CartPromotionItem> cartPromotionItemList, List<Long> productCategoryIds) {
        BigDecimal total = new BigDecimal("0");
        for (CartPromotionItem item : cartPromotionItemList) {
            if(productCategoryIds.contains(item.getProductCategoryId())){
                BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
                total=total.add(realPrice.multiply(new BigDecimal(item.getQuantity())));
            }
        }
        return total;
    }

    /**
     * 计算购物车商品的总价
     * @param cartPromotionItemList 包含商品促销信息 的商品列表
     */
    private BigDecimal calcTotalAmount(List<CartPromotionItem> cartPromotionItemList) {
        BigDecimal total = new BigDecimal("0");
        for (CartPromotionItem item : cartPromotionItemList) {
            BigDecimal realPrice = item.getPrice().subtract(item.getReduceAmount());
            total=total.add(realPrice.multiply(new BigDecimal(item.getQuantity())));
        }
        return total;
    }
}
