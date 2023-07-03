package com.lqs.mall.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 *  参数验证-自定义注解(验证状态是否在自定义范围内)
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD,ElementType.PARAMETER}) // 用在字段和参数上
@Constraint(validatedBy = FlagValidatorClass.class)
public @interface FlagValidator {
    String[] value() default {};
    String message() default "flag not found";
    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
