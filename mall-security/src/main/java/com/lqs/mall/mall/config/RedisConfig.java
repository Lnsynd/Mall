package com.lqs.mall.mall.config;

import com.lqs.mall.common.config.BaseRedisConfig;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

/**
 * Created by 刘千山 on 2023/6/30/030-09:07
 */
@EnableCaching
@Configuration
public class RedisConfig extends BaseRedisConfig {
}
