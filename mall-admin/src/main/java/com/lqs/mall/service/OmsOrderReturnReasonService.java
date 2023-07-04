package com.lqs.mall.service;

import com.lqs.mall.model.OmsOrderReturnReason;

import java.util.List;

/**
 * 订单退货原因Service
 * Created by 刘千山 on 2023/7/4/004-15:14
 */
public interface OmsOrderReturnReasonService {
    /**
     * 添加退货原因
     */
    int create(OmsOrderReturnReason returnReason);

    /**
     * 修改退货原因
     */
    int update(Long id, OmsOrderReturnReason returnReason);

    /**
     * 批量删除退货原因
     */
    int delete(List<Long> ids);

    /**
     * 分页查询退货原因
     */
    List<OmsOrderReturnReason> list(Integer pageSize, Integer pageNum);

    /**
     * 获取单个退货原因详情
     */
    OmsOrderReturnReason getItem(Long id);

    /**
     * 修改退货原因启用状态
     */
    int updateStatus(List<Long> ids, Integer status);
}
