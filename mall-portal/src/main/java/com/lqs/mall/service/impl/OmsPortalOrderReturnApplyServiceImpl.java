package com.lqs.mall.service.impl;

import com.lqs.mall.domain.OmsOrderReturnApplyParam;
import com.lqs.mall.mapper.OmsOrderReturnApplyMapper;
import com.lqs.mall.model.OmsOrderReturnApply;
import com.lqs.mall.service.OmsPortalOrderReturnApplyService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 前台申请退货实现类
 * Created by 刘千山 on 2023/7/4/004-20:25
 */
@Service
public class OmsPortalOrderReturnApplyServiceImpl implements OmsPortalOrderReturnApplyService {
    @Autowired
    private OmsOrderReturnApplyMapper returnApplyMapper;
    @Override
    public int create(OmsOrderReturnApplyParam returnApply) {
        OmsOrderReturnApply realApply = new OmsOrderReturnApply();
        BeanUtils.copyProperties(returnApply,realApply);
        realApply.setCreateTime(new Date());
        realApply.setStatus(0);
        return returnApplyMapper.insert(realApply);
    }
}
