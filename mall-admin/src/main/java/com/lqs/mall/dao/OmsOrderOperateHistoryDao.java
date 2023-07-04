package com.lqs.mall.dao;

import com.lqs.mall.model.OmsOrderOperateHistory;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单操作记录 自定义dao
 * Created by 刘千山 on 2023/7/4/004-11:18
 */
public interface OmsOrderOperateHistoryDao {
    /**
     * 插入订单操作历史记录
     */
    int insertList(@Param("list") List<OmsOrderOperateHistory> operateHistory);
}
