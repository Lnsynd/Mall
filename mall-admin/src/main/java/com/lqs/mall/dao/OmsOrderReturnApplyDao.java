package com.lqs.mall.dao;

import com.lqs.mall.dto.OmsOrderReturnApplyResult;
import com.lqs.mall.dto.OmsReturnApplyQueryParam;
import com.lqs.mall.model.OmsOrderReturnApply;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 退货订单管理 自定义dao
 * Created by 刘千山 on 2023/7/4/004-14:53
 */
public interface OmsOrderReturnApplyDao {
    /**
     * 获取申请退货列表
     */
    List<OmsOrderReturnApply> list(@Param("queryParam") OmsReturnApplyQueryParam queryParam);

    /**
     * 获取退货订单详情
     */
    OmsOrderReturnApplyResult getDetail(Long id);
}
