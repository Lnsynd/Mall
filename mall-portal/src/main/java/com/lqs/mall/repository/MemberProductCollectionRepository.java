package com.lqs.mall.repository;

import com.lqs.mall.domain.MemberProductCollection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 会员收藏商品Repository
 * Created by 刘千山 on 2023/7/5/005-11:18
 */
public interface MemberProductCollectionRepository extends MongoRepository<MemberProductCollection, String> {
    /**
     * 根据会员ID和商品ID查找记录
     */
    MemberProductCollection findByMemberIdAndProductId(Long memberId, Long productId);

    /**
     * 根据会员ID和商品ID删除记录
     */
    int deleteByMemberIdAndProductId(Long id, Long productId);

    /**
     * 根据会员ID分页查询记录
     */
    Page<MemberProductCollection> findByMemberId(Long id, Pageable pageable);

    /**
     * 根据会员ID删除记录
     */
    void deleteAllByMemberId(Long id);
}
