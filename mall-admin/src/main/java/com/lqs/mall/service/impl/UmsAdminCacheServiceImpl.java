package com.lqs.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.lqs.mall.common.service.RedisService;
import com.lqs.mall.mapper.UmsAdminRoleRelationMapper;
import com.lqs.mall.model.UmsAdmin;
import com.lqs.mall.model.UmsAdminRoleRelation;
import com.lqs.mall.model.UmsAdminRoleRelationExample;
import com.lqs.mall.model.UmsResource;
import com.lqs.mall.service.UmsAdminCacheService;
import com.lqs.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 后台用户 缓存实现类
 * Created by 刘千山 on 2023/7/2/002-13:19
 */
@Service
public class UmsAdminCacheServiceImpl implements UmsAdminCacheService {

    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UmsAdminService adminService;

    @Value("${redis.database}")
    private String REDIS_DATABASE;

    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;

    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;

    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;

    @Override
    public UmsAdmin getAdmin(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
        return (UmsAdmin) redisService.get(key);
    }

    @Override
    public void setAdmin(UmsAdmin admin) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisService.set(key, admin, REDIS_EXPIRE);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        return (List<UmsResource>) redisService.get(key);
    }

    @Override
    public void setResourceList(Long adminId, List<UmsResource> resourceList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.set(key, resourceList, REDIS_EXPIRE);
    }

    @Override
    public void delAdmin(Long id) {
        UmsAdmin admin = adminService.getItem(id);
        if (admin != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
            redisService.del(key);
        }
    }

    @Override
    public void delResourceList(Long id) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + id;
        redisService.del(key);
    }

    @Override
    public void delResourceListByRoleIds(List<Long> ids) {
        //  查询存在该 角色 id 的 用户
        UmsAdminRoleRelationExample example = new UmsAdminRoleRelationExample();
        example.createCriteria().andRoleIdIn(ids);
        List<UmsAdminRoleRelation> adminRoleList = adminRoleRelationMapper.selectByExample(example);
        if(CollUtil.isNotEmpty(adminRoleList)){
            // 删除redis中的数据
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN +":";
            List<String> keys = adminRoleList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }


    }

    @Override
    public void delResourceListByRole(Long roleId) {
        // 查询与该 角色id 有关的用户
        UmsAdminRoleRelationExample example = new UmsAdminRoleRelationExample();
        example.createCriteria().andRoleIdEqualTo(roleId);
        List<UmsAdminRoleRelation> relationList = adminRoleRelationMapper.selectByExample(example);
        if (CollUtil.isNotEmpty(relationList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }
}
