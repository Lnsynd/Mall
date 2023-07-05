package com.lqs.mall.service;

import com.lqs.mall.domain.MemberReadHistory;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 会员浏览记录管理Service
 * Created by 刘千山 on 2023/7/5/005-10:52
 */
public interface MemberReadHistoryService {
    /**
     * 生成浏览记录
     */
    int create(MemberReadHistory memberReadHistory);

    /**
     * 批量删除浏览记录
     */
    int delete(List<String> ids);

    /**
     * 分页获取用户浏览历史记录
     */
    Page<MemberReadHistory> list(Integer pageNum, Integer pageSize);

    /**
     * 清空浏览记录
     */
    void clear();
}
