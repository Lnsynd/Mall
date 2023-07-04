package com.lqs.mall.config;

import com.lqs.mall.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * Created by 刘千山 on 2023/7/3/003-20:36
 */
@Configuration
public class MallSecurityConfig {

    @Autowired
    private UmsMemberService memberService;
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> memberService.loadUserByUsername(username);
    }
}
