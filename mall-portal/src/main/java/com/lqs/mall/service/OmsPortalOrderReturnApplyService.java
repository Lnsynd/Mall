package com.lqs.mall.service;

import com.lqs.mall.domain.OmsOrderReturnApplyParam;

/**
 * 前台 申请退货Service
 * Created by 刘千山 on 2023/7/4/004-20:25
 */
public interface OmsPortalOrderReturnApplyService {

    /**
     * 提交退货申请
     */
    int create(OmsOrderReturnApplyParam returnApply);
}
