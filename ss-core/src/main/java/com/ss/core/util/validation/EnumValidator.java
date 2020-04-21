package com.ss.core.util.validation;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**
 * @author zxk
 */
public class EnumValidator implements ConstraintValidator<Enum, String> {

    private Class<?> enumClass;

    private String fieldName;

    @Override
    public void initialize(Enum constraintAnnotation) {
        enumClass = constraintAnnotation.enumClass();
        fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        if(!enumClass.isEnum()){
            throw new ClassCastException("null enum error");
        }

        if (value == null) {
            throw new NullPointerException("null enum error");
        }

        return ValidatorUtils.checkEnum(value, enumClass, fieldName);
    }
}
