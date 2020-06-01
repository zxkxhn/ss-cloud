package com.ss.core.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomizeException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String msg;
    private int code = 500;

    public CustomizeException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public CustomizeException(String msg, Throwable e) {
        super(msg, e);
        this.msg = msg;
    }

    public CustomizeException(String msg, int code) {
        super(msg);
        this.msg = msg;
        this.code = code;
    }

    public CustomizeException(String msg, int code, Throwable e) {
        super(msg, e);
        this.msg = msg;
        this.code = code;
    }
}
