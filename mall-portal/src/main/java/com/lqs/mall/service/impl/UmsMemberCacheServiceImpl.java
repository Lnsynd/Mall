package com.lqs.mall.service.impl;

import com.lqs.mall.common.service.RedisService;
import com.lqs.mall.mapper.UmsMemberMapper;
import com.lqs.mall.model.UmsMember;
import com.lqs.mall.service.UmsMemberCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * 会员管理缓存实现类
 * Created by 刘千山 on 2023/7/3/003-20:42
 */
@Service
public class UmsMemberCacheServiceImpl implements UmsMemberCacheService {
    @Autowired
    private RedisService redisService;
    @Autowired
    private UmsMemberMapper memberMapper;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.expire.authCode}")
    private Long REDIS_EXPIRE_AUTH_CODE;
    @Value("${redis.key.member}")
    private String REDIS_KEY_MEMBER;
    @Value("${redis.key.authCode}")
    private String REDIS_KEY_AUTH_CODE;

    @Override
    public String getAuthCode(String telephone) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + telephone;
        return (String) redisService.get(key);
    }

    @Override
    public void setAuthCode(String telephone, String authCode) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_AUTH_CODE + ":" + telephone;
        redisService.set(key, authCode, REDIS_EXPIRE_AUTH_CODE);
    }

    @Override
    public void delMember(Long id) {
        UmsMember umsMember = memberMapper.selectByPrimaryKey(id);
        // 如果存在数据 ，则删除
        if (umsMember != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + umsMember.getUsername();
            redisService.del(key);
        }

    }

    @Override
    public UmsMember getMember(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + username;
        return (UmsMember) redisService.get(key);
    }

    @Override
    public void setMember(UmsMember member) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_MEMBER + ":" + member.getId();
        redisService.set(key, member, REDIS_EXPIRE);
    }


}
