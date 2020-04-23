package com.ss.core.exception;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public enum SystemErrorTypeEnum {

    /**
     * code 返回码
     * msg 错误信息
     */
    SUCCESS(200, "成功"),
    VALID_FORM(201, "参数校验失败"),
    JWT_TOKEN_ERROR(401, "Token异常,重新登录"),
    PERMISSIONS_ERROR(403, "无权限,请联系管理员"),
    SYSTEM_ERROR(500, "系统异常"),


    ;

    /**
     * 错误类型码
     */
    private int code;
    /**
     * 错误类型描述信息
     */
    private String msg;

    SystemErrorTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static Map<Integer, SystemErrorTypeEnum> map = new HashMap<>();

    static {
        for (SystemErrorTypeEnum e : values()) {
            map.put(e.getCode(), e);
        }
    }


    public static SystemErrorTypeEnum valueOf(int code) {
        return map.get(code);
    }
}