package com.lqs.mall.config;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.lqs.mall.common.config.BaseSwaggerConfig;
import com.lqs.mall.common.domain.SwaggerProperties;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger相关配置
 * Created by 刘千山 on 2023/6/29/029-20:23
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig extends BaseSwaggerConfig {
    @Override
    public SwaggerProperties swaggerProperties() {
        return SwaggerProperties.builder()
                .apiBasePackage("com.lqs.mall.controller")
                .title("mall项目后台系统")
                .description("mall后台相关接口文档")
                .contactName("刘千山")
                .contactEmail("2034215871@qq.com")
                .enableSecurity(true)
                .build();
    }


    @Bean
    public BeanPostProcessor springfoxHandlerProviderBeanPostProcessor() {
        return generateBeanPostProcessor();
    }
}
