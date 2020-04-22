package com.ss.core.config.security.exception;

import lombok.Getter;
import org.springframework.security.authentication.InternalAuthenticationServiceException;

@Getter
public class LoginFailLimitException extends InternalAuthenticationServiceException {

    private static final long serialVersionUID = -6258237374010415276L;

    private String msg;

    public LoginFailLimitException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public LoginFailLimitException(String msg, Throwable t) {
        super(msg, t);
        this.msg = msg;
    }
}
