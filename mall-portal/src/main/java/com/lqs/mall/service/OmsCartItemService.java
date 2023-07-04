package com.lqs.mall.service;

import com.lqs.mall.domain.CartProduct;
import com.lqs.mall.domain.CartPromotionItem;
import com.lqs.mall.model.OmsCartItem;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 商城购物车管理Service
 * Created by 刘千山 on 2023/7/4/004-15:28
 */
public interface OmsCartItemService {
    /**
     * 添加商品到购物车
     */
    @Transactional
    int add(OmsCartItem cartItem);

    /**
     * 获取当前会员的购物车列表
     */
    List<OmsCartItem> list(Long id);

    /**
     * 获取当前会员的购物车信息（包括促销信息）
     */
    List<CartPromotionItem> listPromotion(Long id, List<Long> cartIds);

    /**
     * 修改购物车中指定商品的数量
     * @param id 商品id
     * @param memberId 用户id
     * @param quantity 商品数量
     * @return
     */
    int updateQuantity(Long id, Long memberId, Integer quantity);

    /**
     * 获取购物车中指定物品的属性和规格信息
     */
    CartProduct getCartProduct(Long productId);

    /**
     * 修改购物车中商品的属性
     */
    @Transactional
    int updateAttr(OmsCartItem cartItem);

    /**
     * 删除购物车中的指定商品
     * @param memberId 用户id
     * @param ids 商品id
     */
    int delete(Long memberId, List<Long> ids);

    /**
     * 清空某个用户的购物车信息
     */
    int clear(Long memberId);
}
