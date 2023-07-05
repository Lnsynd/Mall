package com.lqs.mall.service.impl;

import com.lqs.mall.mapper.UmsMemberReceiveAddressMapper;
import com.lqs.mall.model.UmsMember;
import com.lqs.mall.model.UmsMemberReceiveAddress;
import com.lqs.mall.model.UmsMemberReceiveAddressExample;
import com.lqs.mall.service.UmsMemberReceiveAddressService;
import com.lqs.mall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 用户地址管理实现类
 * Created by 刘千山 on 2023/7/4/004-17:48
 */
@Service
public class UmsMemberReceiveAddressServiceImpl implements UmsMemberReceiveAddressService {

    @Autowired
    private UmsMemberService memberService;

    @Autowired
    private UmsMemberReceiveAddressMapper addressMapper;
    @Override
    public List<UmsMemberReceiveAddress> list() {
        UmsMember currentMember = memberService.getCurrentMember();
        UmsMemberReceiveAddressExample example = new UmsMemberReceiveAddressExample();
        example.createCriteria().andMemberIdEqualTo(currentMember.getId());
        return addressMapper.selectByExample(example);
    }

    @Override
    public UmsMemberReceiveAddress getItem(Long memberReceiveAddressId) {
        UmsMember currentMember = memberService.getCurrentMember();
        UmsMemberReceiveAddressExample example = new UmsMemberReceiveAddressExample();
        example.createCriteria().andMemberIdEqualTo(currentMember.getId()).andIdEqualTo(memberReceiveAddressId);
        List<UmsMemberReceiveAddress> addressList = addressMapper.selectByExample(example);
        if(!CollectionUtils.isEmpty(addressList)){
            return addressList.get(0);
        }
        return null;
    }

    @Override
    public int add(UmsMemberReceiveAddress address) {
        UmsMember currentMember = memberService.getCurrentMember();
        address.setMemberId(currentMember.getId());
        return addressMapper.insert(address);
    }

    @Override
    public int delete(Long id) {
        UmsMember currentMember = memberService.getCurrentMember();
        UmsMemberReceiveAddressExample example = new UmsMemberReceiveAddressExample();
        example.createCriteria().andMemberIdEqualTo(currentMember.getId()).andIdEqualTo(id);
        return addressMapper.deleteByExample(example);
    }

    @Override
    public int update(Long id, UmsMemberReceiveAddress address) {
        address.setId(null);
        UmsMember currentMember = memberService.getCurrentMember();
        UmsMemberReceiveAddressExample example = new UmsMemberReceiveAddressExample();
        example.createCriteria().andMemberIdEqualTo(currentMember.getId()).andIdEqualTo(id);
        if(address.getDefaultStatus()==1){
            //先将原来的默认地址去除
            UmsMemberReceiveAddress record= new UmsMemberReceiveAddress();
            record.setDefaultStatus(0);
            UmsMemberReceiveAddressExample updateExample = new UmsMemberReceiveAddressExample();
            updateExample.createCriteria()
                    .andMemberIdEqualTo(currentMember.getId())
                    .andDefaultStatusEqualTo(1);
            addressMapper.updateByExampleSelective(record,updateExample);
        }
        return addressMapper.updateByExampleSelective(address,example);
    }
}
