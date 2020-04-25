package com.ss.security.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Exrickx
 */
@Slf4j
public class ResponseUtil {

    /**
     * 使用response输出JSON
     */
    public static void out(HttpServletResponse response, int code, String msg) {
        out(response, code, msg, null);
    }

    public static void out(HttpServletResponse response, int code, String msg, String data) {
        try (ServletOutputStream out = response.getOutputStream()) {
            response.setCharacterEncoding("UTF-8");
            response.setContentType("application/json;charset=UTF-8");
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", code);
            jsonObject.put("msg", msg);
            jsonObject.put("data", data);
            out.write(jsonObject.toJSONString().getBytes());
        } catch (Exception e) {
            log.error("ResponseUtil out error: ", e);
        }
    }
}
