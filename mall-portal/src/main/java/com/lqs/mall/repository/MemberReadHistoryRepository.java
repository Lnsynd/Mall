package com.lqs.mall.repository;

import com.lqs.mall.domain.MemberReadHistory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * 会员商品浏览记录Repository
 * Created by 刘千山 on 2023/7/5/005-10:54
 */
public interface MemberReadHistoryRepository extends MongoRepository<MemberReadHistory,String> {
    /**
     * 根据会员ID分页查找记录
     */
    Page<MemberReadHistory> findByMemberIdOrderByCreateTimeDesc(Long id, Pageable pageable);

    /**
     * 根据会员ID删除记录
     */
    void deleteAllByMemberId(Long id);
}
