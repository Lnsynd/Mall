package com.lqs.mall.config;

import com.lqs.mall.service.UmsAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by 刘千山 on 2023/6/30/030-15:11
 */

@Configuration
public class MallSecurityConfig {

    @Autowired
    private UmsAdminService adminService;


    @Bean
    public UserDetailsService userDetailsService(){
        // 获取登录用户
        return username -> adminService.loadUserByUsername(username);
    }

    //TODO  动态代理实现

}
