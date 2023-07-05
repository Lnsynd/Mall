package com.lqs.mall.service.impl;

import com.lqs.mall.domain.MemberBrandAttention;
import com.lqs.mall.mapper.PmsBrandMapper;
import com.lqs.mall.model.PmsBrand;
import com.lqs.mall.model.UmsMember;
import com.lqs.mall.repository.MemberBrandAttentionRepository;
import com.lqs.mall.service.MemberAttentionService;
import com.lqs.mall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 会员品牌关注 管理实现类
 * Created by 刘千山 on 2023/7/5/005-11:03
 */
@Service
public class MemberAttentionServiceImpl implements MemberAttentionService {
    @Value("${mongo.insert.sqlEnable}")
    private Boolean sqlEnable;
    @Autowired
    private PmsBrandMapper brandMapper;
    @Autowired
    private MemberBrandAttentionRepository memberBrandAttentionRepository;
    @Autowired
    private UmsMemberService memberService;

    @Override
    public int add(MemberBrandAttention memberBrandAttention) {
        int count = 0;
        if (memberBrandAttention.getBrandId() == null) {
            return 0;
        }
        UmsMember member = memberService.getCurrentMember();
        memberBrandAttention.setMemberId(member.getId());
        memberBrandAttention.setMemberNickname(member.getNickname());
        memberBrandAttention.setMemberIcon(member.getIcon());
        memberBrandAttention.setCreateTime(new Date());
        // 查看repository中是否  该用户关注了brand
        MemberBrandAttention attention = memberBrandAttentionRepository.findByMemberIdAndBrandId(member.getId(), memberBrandAttention.getBrandId());
        if (attention == null) {
            // 如果不存在， 数据库查询后 存入 repository
            if (sqlEnable) {
                PmsBrand pmsBrand = brandMapper.selectByPrimaryKey(memberBrandAttention.getBrandId());
                if (pmsBrand == null) {
                    return 0;
                } else {
                    memberBrandAttention.setBrandCity(null);
                    memberBrandAttention.setBrandName(pmsBrand.getName());
                    memberBrandAttention.setBrandLogo(pmsBrand.getLogo());
                }
            }
            memberBrandAttentionRepository.save(memberBrandAttention);
            count = 1;
        }
        return count;
    }

    @Override
    public int delete(Long brandId) {
        UmsMember member = memberService.getCurrentMember();
        return memberBrandAttentionRepository.deleteByMemberIdAndBrandId(member.getId(),brandId);
    }

    @Override
    public Page<MemberBrandAttention> list(Integer pageNum, Integer pageSize) {
        UmsMember member = memberService.getCurrentMember();
        Pageable pageable = PageRequest.of(pageNum-1,pageSize);
        return memberBrandAttentionRepository.findByMemberId(member.getId(),pageable);
    }

    @Override
    public MemberBrandAttention detail(Long brandId) {
        UmsMember member = memberService.getCurrentMember();
        return memberBrandAttentionRepository.findByMemberIdAndBrandId(member.getId(), brandId);
    }

    @Override
    public void clear() {
        UmsMember member = memberService.getCurrentMember();
        memberBrandAttentionRepository.deleteAllByMemberId(member.getId());
    }



}
