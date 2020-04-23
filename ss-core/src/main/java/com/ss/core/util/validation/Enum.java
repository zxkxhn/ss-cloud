package com.ss.core.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * 电话号码格式校验
 *
 * @Constraint注解表明是由哪个类进行校验 groups和payload为必须
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Constraint(validatedBy = {IsMobileValidator.class})
public @interface Enum {

    /**
     * 是否需要校验
     *
     * @return boolean
     */
    boolean required() default true;

    String message() default "枚举类型错误";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * 枚举类
     */
    Class<? extends java.lang.Enum<?>> enumClass();

    /**
     * 枚举字段
     */
    String fieldName() default "name";

}
