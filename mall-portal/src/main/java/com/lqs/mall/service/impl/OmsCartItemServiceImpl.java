package com.lqs.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.lqs.mall.controller.OmsCartItemController;
import com.lqs.mall.dao.PortalProductDao;
import com.lqs.mall.domain.CartProduct;
import com.lqs.mall.domain.CartPromotionItem;
import com.lqs.mall.mapper.OmsCartItemMapper;
import com.lqs.mall.model.OmsCartItem;
import com.lqs.mall.model.OmsCartItemExample;
import com.lqs.mall.model.UmsMember;
import com.lqs.mall.service.OmsCartItemService;
import com.lqs.mall.service.OmsPromotionService;
import com.lqs.mall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 商城购物车管理Service实现类
 * Created by 刘千山 on 2023/7/4/004-15:28
 */
@Service
public class OmsCartItemServiceImpl implements OmsCartItemService {
    @Autowired
    private OmsCartItemMapper cartItemMapper;

    @Autowired
    private UmsMemberService memberService;

    @Autowired
    private OmsPromotionService promotionService;

    @Autowired
    private PortalProductDao productDao;

    @Override
    public int add(OmsCartItem cartItem) {
        // 先给购物车的物品赋值
        int count = 0;
        UmsMember currentMember = memberService.getCurrentMember();
        cartItem.setMemberId(cartItem.getMemberId());
        cartItem.setMemberNickname(cartItem.getMemberNickname());
        cartItem.setDeleteStatus(0);
        // 查看购物车是否存在该物品
        OmsCartItem existedItem = getCartItem(cartItem);
        // 如果不存在，则加入
        if (existedItem == null) {
            cartItem.setCreateDate(new Date());
            cartItemMapper.insert(cartItem);
        } else {
            // 如果存在 ，则数量增加
            existedItem.setModifyDate(new Date());
            existedItem.setQuantity(existedItem.getQuantity() + cartItem.getQuantity());
            count = cartItemMapper.updateByPrimaryKey(existedItem);
        }
        return count;
    }

    @Override
    public List<OmsCartItem> list(Long id) {
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(id).andDeleteStatusEqualTo(0);
        return cartItemMapper.selectByExample(example);
    }

    @Override
    public List<CartPromotionItem> listPromotion(Long id, List<Long> cartIds) {
        // 获取当前用户的购物车列表
        List<OmsCartItem> cartItemList = list(id);
        // 过滤出 ?id
        if(CollUtil.isNotEmpty(cartIds)){
            cartItemList = cartItemList.stream().filter(item->cartIds.contains(item.getId())).collect(Collectors.toList());
        }
        List<CartPromotionItem> cartPromotionItemList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(cartItemList)){
            cartPromotionItemList = promotionService.calcCartPromotion(cartItemList);
        }
        return cartPromotionItemList;
    }

    /**
     * 根据会员的id，昵称和规格获取购物车中商品
     */
    private OmsCartItem getCartItem(OmsCartItem cartItem) {
        OmsCartItemExample example = new OmsCartItemExample();
        OmsCartItemExample.Criteria criteria = example.createCriteria();
        criteria
                .andMemberIdEqualTo(cartItem.getMemberId())
                .andProductIdEqualTo(cartItem.getProductId())
                .andDeleteStatusEqualTo(0);
        if (cartItem.getProductSkuId() != null) {
            criteria.andProductSkuIdEqualTo(cartItem.getProductSkuId());
        }
        List<OmsCartItem> cartItemList = cartItemMapper.selectByExample(example);
        if (!CollectionUtils.isEmpty(cartItemList)) {
            return cartItemList.get(0);
        }
        return null;
    }

    @Override
    public int updateQuantity(Long id, Long memberId, Integer quantity) {
        OmsCartItem cartItem = new OmsCartItem();
        cartItem.setQuantity(quantity);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andDeleteStatusEqualTo(0)
                .andIdEqualTo(id).andMemberIdEqualTo(memberId);
        return cartItemMapper.updateByExampleSelective(cartItem, example);
    }

    @Override
    public CartProduct getCartProduct(Long productId) {
        return productDao.getCartProduct(productId);
    }

    @Override
    public int updateAttr(OmsCartItem cartItem) {
        //删除原购物车信息
        OmsCartItem updateCart = new OmsCartItem();
        updateCart.setId(cartItem.getId());
        updateCart.setModifyDate(new Date());
        updateCart.setDeleteStatus(1);
        cartItemMapper.updateByPrimaryKeySelective(updateCart);
        cartItem.setId(null);
        // 添加新的信息
        add(cartItem);
        return 1;
    }

    @Override
    public int delete(Long memberId, List<Long> ids) {
        OmsCartItem record = new OmsCartItem();
        record.setDeleteStatus(1);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andIdIn(ids).andMemberIdEqualTo(memberId);
        return cartItemMapper.updateByExampleSelective(record, example);
    }

    @Override
    public int clear(Long memberId) {
        OmsCartItem record = new OmsCartItem();
        record.setDeleteStatus(1);
        OmsCartItemExample example = new OmsCartItemExample();
        example.createCriteria().andMemberIdEqualTo(memberId);
        return cartItemMapper.updateByExampleSelective(record,example);
    }
}
