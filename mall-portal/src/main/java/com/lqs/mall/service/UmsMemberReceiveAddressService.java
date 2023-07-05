package com.lqs.mall.service;

import com.lqs.mall.model.UmsMemberReceiveAddress;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户地址管理Service
 * Created by 刘千山 on 2023/7/4/004-17:47
 */
public interface    UmsMemberReceiveAddressService {
    /**
     * 返回当前用户的收货地址
     */
    List<UmsMemberReceiveAddress> list();

    /**
     * 获取地址详情
     * @param memberReceiveAddressId 地址id
     */
    UmsMemberReceiveAddress getItem(Long memberReceiveAddressId);

    /**
     * 添加收货地址
     */
    int add(UmsMemberReceiveAddress address);

    /**
     * 删除收货地址
     * @param id 地址表的id
     */
    int delete(Long id);

    /**
     * 修改收货地址
     * @param id 地址表的id
     * @param address 修改的收货地址信息
     */
    @Transactional
    int update(Long id, UmsMemberReceiveAddress address);
}
