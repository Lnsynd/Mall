package com.lqs.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * MyBatis配置
 * Created by 刘千山 on 2023/7/3/003-20:34
 */
@Configuration
@EnableTransactionManagement
@MapperScan({"com.lqs.mall.mapper","com.lqs.mall.dao"})
public class MyBatisConfig {
}
