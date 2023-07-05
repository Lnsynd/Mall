package com.lqs.mall.repository;

import com.lqs.mall.domain.MemberBrandAttention;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 会员关注品牌Repository
 * Created by 刘千山 on 2023/7/5/005-11:06
 */
public interface MemberBrandAttentionRepository extends MongoRepository<MemberBrandAttention,String> {
    /**
     * 根据会员ID和品牌ID查找记录
     */
    MemberBrandAttention findByMemberIdAndBrandId(Long memberId,Long brandId);

    /**
     * 根据会员id和品牌id删除 记录
     */
    int deleteByMemberIdAndBrandId(Long memberId, Long brandId);

    /**
     * 获取用户的关注列表
     */
    Page<MemberBrandAttention> findByMemberId(Long memberId, Pageable pageable);

    /**
     * 删除该用户的全部关注列表
     */
    void deleteAllByMemberId(Long memberId);
}
