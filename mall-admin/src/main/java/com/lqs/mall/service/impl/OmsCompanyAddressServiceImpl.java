package com.lqs.mall.service.impl;

import com.lqs.mall.mapper.OmsCompanyAddressMapper;
import com.lqs.mall.model.OmsCompanyAddress;
import com.lqs.mall.model.OmsCompanyAddressExample;
import com.lqs.mall.service.OmsCompanyAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地址管理实现类
 * Created by 刘千山 on 2023/7/5/005-19:27
 */
@Service
public class OmsCompanyAddressServiceImpl implements OmsCompanyAddressService {
    @Autowired
    private OmsCompanyAddressMapper companyAddressMapper;
    @Override
    public List<OmsCompanyAddress> list() {
        return companyAddressMapper.selectByExample(new OmsCompanyAddressExample());
    }
}
