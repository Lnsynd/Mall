package com.lqs.mall.service.impl;

import com.lqs.mall.dto.SmsFlashPromotionSessionDetail;
import com.lqs.mall.mapper.SmsFlashPromotionSessionMapper;
import com.lqs.mall.model.SmsFlashPromotionSession;
import com.lqs.mall.model.SmsFlashPromotionSessionExample;
import com.lqs.mall.service.SmsFlashPromotionProductRelationService;
import com.lqs.mall.service.SmsFlashPromotionSessionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 限时购活动实现类
 * Created by 刘千山 on 2023/7/4/004-21:05
 */
@Service
public class SmsFlashPromotionSessionServiceImpl implements SmsFlashPromotionSessionService {
    @Autowired
    private SmsFlashPromotionSessionMapper promotionSessionMapper;
    @Autowired
    private SmsFlashPromotionProductRelationService relationService;

    @Override
    public int create(SmsFlashPromotionSession promotionSession) {
        promotionSession.setCreateTime(new Date());
        return promotionSessionMapper.insert(promotionSession);
    }

    @Override
    public int update(Long id, SmsFlashPromotionSession promotionSession) {
        promotionSession.setId(id);
        return promotionSessionMapper.updateByPrimaryKey(promotionSession);
    }

    @Override
    public int updateStatus(Long id, Integer status) {
        SmsFlashPromotionSession promotionSession = new SmsFlashPromotionSession();
        promotionSession.setId(id);
        promotionSession.setStatus(status);
        return promotionSessionMapper.updateByPrimaryKeySelective(promotionSession);
    }

    @Override
    public int delete(Long id) {
        return promotionSessionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public SmsFlashPromotionSession getItem(Long id) {
        return promotionSessionMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<SmsFlashPromotionSession> list() {
        SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
        return promotionSessionMapper.selectByExample(example);
    }

    @Override
    public List<SmsFlashPromotionSessionDetail> selectList(Long flashPromotionId) {
        List<SmsFlashPromotionSessionDetail> result = new ArrayList<>();
        SmsFlashPromotionSessionExample example = new SmsFlashPromotionSessionExample();
        example.createCriteria().andStatusEqualTo(1);
        List<SmsFlashPromotionSession> list = promotionSessionMapper.selectByExample(example);
        for (SmsFlashPromotionSession promotionSession : list) {
            SmsFlashPromotionSessionDetail detail = new SmsFlashPromotionSessionDetail();
            BeanUtils.copyProperties(promotionSession, detail);
            long count = relationService.getCount(flashPromotionId, promotionSession.getId());
            detail.setProductCount(count);
            result.add(detail);
        }
        return result;
    }
}
