package com.lqs.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis
 * Created by 刘千山 on 2023/6/30/030-15:08
 */
@Configuration
@MapperScan({"com.lqs.mall.dao","com.lqs.mall.mapper"})
public class MyBatisConfig {
}
