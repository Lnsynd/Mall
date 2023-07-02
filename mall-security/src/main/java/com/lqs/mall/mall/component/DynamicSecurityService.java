package com.lqs.mall.mall.component;

import org.springframework.security.access.ConfigAttribute;

import java.util.Map;

/**
 * 动态权限相关业务接口
 * Created by 刘千山 on 2023/7/2/002-16:35
 */
public interface DynamicSecurityService {
    /**
     * 加载资源
     */
    Map<String, ConfigAttribute> loadDataSource();
}
