package com.lqs.mall.mall.annotation;

import net.bytebuddy.implementation.bind.annotation.RuntimeType;

import java.lang.annotation.*;

@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface CacheException {
}
