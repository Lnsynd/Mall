package com.lqs.mall.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

/**
 * MyBatis配置
 * Created by 刘千山 on 2023/7/5/005-15:03
 */
@Configuration
@MapperScan({"com.lqs.mall.mapper","com.lqs.mall.dao"})
public class MyBatisConfig {
}
