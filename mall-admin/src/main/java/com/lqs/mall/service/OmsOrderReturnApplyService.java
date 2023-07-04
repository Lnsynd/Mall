package com.lqs.mall.service;

import com.lqs.mall.dto.OmsOrderReturnApplyResult;
import com.lqs.mall.dto.OmsReturnApplyQueryParam;
import com.lqs.mall.dto.OmsUpdateStatusParam;
import com.lqs.mall.model.OmsOrderReturnApply;

import java.util.List;

/**
 * 订单申请退货Service
 * Created by 刘千山 on 2023/7/4/004-14:50
 */
public interface OmsOrderReturnApplyService {
    /**
     * 分页获取订单退货列表
     */
    List<OmsOrderReturnApply> list(OmsReturnApplyQueryParam queryParam, Integer pageSize, Integer pageNum);

    /**
     * 批量删除订单退货列表
     */
    int delete(List<Long> ids);

    /**
     * 获取订单退货详情
     */
    OmsOrderReturnApplyResult getItem(Long id);

    /**
     * 修改订单退货状态
     */
    int updateStatus(Long id, OmsUpdateStatusParam statusParam);
}
