package com.lqs.mall.service;

import com.lqs.mall.model.OmsCompanyAddress;

import java.util.List;

/**
 * 地址管理Service
 * Created by 刘千山 on 2023/7/5/005-19:27
 */
public interface OmsCompanyAddressService {

    /**
     * 获取全部收货地址
     */
    List<OmsCompanyAddress> list();
}
