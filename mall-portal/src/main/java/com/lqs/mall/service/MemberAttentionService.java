package com.lqs.mall.service;

import com.lqs.mall.domain.MemberBrandAttention;
import org.springframework.data.domain.Page;

/**
 * 会员关注品牌 管理Service
 * Created by 刘千山 on 2023/7/5/005-11:01
 */
public interface MemberAttentionService {

    /**
     * 添加品牌 关注
     */
    int add(MemberBrandAttention memberBrandAttention);

    /**
     * 取消关注
     */
    int delete(Long brandId);

    /**
     * 获取用户关注列表
     */
    Page<MemberBrandAttention> list(Integer pageNum, Integer pageSize);

    /**
     * 获取用户关注详情
     */
    MemberBrandAttention detail(Long brandId);

    /**
     * 清空关注列表
     */
    void clear();
}
