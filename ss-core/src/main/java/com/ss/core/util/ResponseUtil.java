package com.ss.core.util;

import com.alibaba.fastjson.JSONObject;
import com.ss.core.common.Result;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Exrickx
 */
@Slf4j
public class ResponseUtil<T> {

    /**
     * 使用response输出JSON
     */
    public static <T> void out(HttpServletResponse response, Result<T> result) {
        try (ServletOutputStream out = response.getOutputStream()) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            out.write(JSONObject.toJSONBytes(result));
        } catch (Exception e) {
            log.error("ResponseUtil out error: ", e);
        }
    }

    public static <T> Result<T> resultMap(boolean flag, int code, String msg) {
        return resultMap(flag, code, msg, null);
    }

    public static <T> Result<T> resultMap(boolean flag, int code, String msg, T data) {
        Result<T> result;
        if (flag) {
            result = Result.success();
        } else {
            result = Result.fail();
        }
        result.setMsg(msg);
        result.setData(data);
        return result;
    }
}
