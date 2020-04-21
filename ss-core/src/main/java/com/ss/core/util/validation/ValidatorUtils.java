package com.ss.core.util.validation;


import cn.hutool.core.util.ReflectUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ValidatorUtils {

    /**
     * 校验手机号码格式是否正确
     *
     * @param mobile 需要校验的手机号码
     * @return boolean
     */
    public static boolean isMobile(String mobile) {
        String regex = "^0?(13|14|15|17|18|19)[0-9]{9}$";
        if (mobile.length() != 11) {
            return false;
        } else {
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(mobile);
            return m.matches();
        }
    }

    /**
     * 校验枚举格式
     */
    public static boolean checkEnum(Object value, Class<?> clazz, String fieldName) {
        final java.lang.Enum<?>[] enums = (java.lang.Enum<?>[]) clazz.getEnumConstants();
        if (null == enums) {
            return false;
        }

        final List<Object> list = new ArrayList<>(enums.length);
        for (java.lang.Enum<?> e : enums) {
            list.add(ReflectUtil.getFieldValue(e, fieldName));
        }
        return list.contains(value);
    }


    public static void main(String[] args) {
        System.out.println(isMobile("13011111111"));
        System.out.println(checkEnum("MENU", DemoEnum.class, "name"));
    }

}
