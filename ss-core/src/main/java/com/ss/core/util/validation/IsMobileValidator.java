package com.ss.core.util.validation;


import cn.hutool.core.util.StrUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class IsMobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        // value为空时进行校验
        if (StrUtil.isNotBlank(value)) {// 不为空时进行校验
            return ValidatorUtils.isMobile(value);
        }
        return false;
    }
}
