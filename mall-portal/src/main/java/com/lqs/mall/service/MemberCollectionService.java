package com.lqs.mall.service;

import com.lqs.mall.domain.MemberProductCollection;
import org.springframework.data.domain.Page;

/**
 * 用户商品收藏管理Service
 * Created by 刘千山 on 2023/7/5/005-11:15
 */
public interface MemberCollectionService {
    /**
     * 添加收藏商品
     */
    int add(MemberProductCollection productCollection);

    /**
     * 删除收藏
     */
    int delete(Long productId);

    /**
     * 分页查询收藏
     */
    Page<MemberProductCollection> list(Integer pageNum, Integer pageSize);

    /**
     * 查看收藏详情
     */
    MemberProductCollection detail(Long productId);

    /**
     * 清空收藏
     */
    void clear();
}
