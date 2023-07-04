package com.lqs.mall.dao;

import com.lqs.mall.model.OmsOrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单商品信息管理自定义Dao
 * Created by 刘千山 on 2023/7/4/004-19:24
 */
public interface PortalOrderItemDao {
    /**
     * 批量插入
     */
    int insertList(@Param("list") List<OmsOrderItem> list);
}
