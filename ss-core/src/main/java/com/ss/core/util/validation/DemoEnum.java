package com.ss.core.util.validation;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum DemoEnum {

    /**
     * value 返回码
     * desc  描述
     * 0：目录   1：菜单   2：按钮
     */
    DIRECTORY(0, "目录"),
    MENU(1, "菜单"),
    BUTTON(2, "按钮");

    private int value;
    private String desc;

    DemoEnum(int value, String desc) {
        this.value = value;
        this.desc = desc;
    }

    public static Map<Integer, DemoEnum> map = new HashMap<>();

    static {
        for (DemoEnum e : values()) {
            map.put(e.getValue(), e);
        }
    }


    public static DemoEnum valueOf(int value) {
        return map.get(value);
    }

}
