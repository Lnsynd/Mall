package com.lqs.mall.config;

import com.lqs.mall.mall.component.DynamicSecurityService;
import com.lqs.mall.model.UmsResource;
import com.lqs.mall.service.UmsAdminService;
import com.lqs.mall.service.UmsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 刘千山 on 2023/6/30/030-15:11
 */

@Configuration
public class MallSecurityConfig {

    @Autowired
    private UmsAdminService adminService;


    @Autowired
    private UmsResourceService resourceService;


    @Bean
    public UserDetailsService userDetailsService(){
        // 获取登录用户
        return username -> adminService.loadUserByUsername(username);
    }

    // 动态代理实现权限控制
    @Bean
    public DynamicSecurityService dynamicSecurityService(){
        return new DynamicSecurityService() {
            @Override
            public Map<String, ConfigAttribute> loadDataSource() {
                Map<String,ConfigAttribute> map = new ConcurrentHashMap<>();
                List<UmsResource> resourceList = resourceService.listAll();
                // 将资源信息 以  <请求地址,id:name> 存到map中
                for(UmsResource resource:resourceList){
                    map.put(resource.getUrl(), new SecurityConfig(resource.getId()+":"+resource.getName()));
                }
                return map;
            }
        };
    }

}
