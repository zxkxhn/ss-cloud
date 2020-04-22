package com.ss.core.exception;

import com.alibaba.fastjson.JSONObject;
import com.ss.core.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理自定义异常
     */
    @ExceptionHandler(CustomizeException.class)
    public Result<String> handleCustomizeException(CustomizeException e) {
        log.error(e.getMessage(), e);
        return Result.fail(e.getCode(), e.getMsg());
    }

    /**
     * 参数校验异常
     *
     * @ RequestBody 专用
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验错误： {}", e.getMessage());
        List<FieldError> bindingResult = e.getBindingResult().getFieldErrors();
        Map<String, String> validMap = new HashMap<>(bindingResult.size());
        for (FieldError fieldError : bindingResult) {
            validMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return Result.fail(SystemErrorTypeEnum.VALID_FORM, JSONObject.toJSONString(validMap));
    }


    @ExceptionHandler(BindException.class)
    public Result<String> handleBindExceptionException(BindException e) {
        log.error("参数校验错误： {}", e.getMessage());
        List<FieldError> bindingResult = e.getBindingResult().getFieldErrors();
        Map<String, String> validMap = new HashMap<>(bindingResult.size());
        for (FieldError fieldError : bindingResult) {
            validMap.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        return Result.fail(SystemErrorTypeEnum.VALID_FORM, JSONObject.toJSONString(validMap));
    }


    /**
     * 其他异常处理
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.fail();
    }

}
