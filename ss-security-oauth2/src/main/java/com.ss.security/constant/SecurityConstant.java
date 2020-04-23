package com.ss.security.constant;

/**
 * @author Exrickx
 */
public interface SecurityConstant {

    /**
     * token分割
     */
    String TOKEN_SPLIT = "Bearer";

    /**
     * JWT签名加密key
     */
    String JWT_SIGN_KEY = "jwt";

    /**
     * token参数头
     */
    String HEADER = "accessToken";

    /**
     * 权限参数头
     */
    String AUTHORITIES = "authorities";

    /**
     * 用户选择JWT保存时间参数头
     */
    String SAVE_LOGIN = "saveLogin";

    /**
     * 交互token前缀key
     */
    String TOKEN_PRE = "TOKEN_PRE:";

    /**
     * 用户token前缀key 单点登录使用
     */
    String USER_TOKEN = "USER_TOKEN:";

    /**
     * 验证码 ID
     */
    String LOGIN_CAPTCHA_ID = "LOGIN_CAPTCHA_ID:";

    /**
     * 超级管理员用户账号
     */
    String LOGIN_ADMIN = "admin";
}
