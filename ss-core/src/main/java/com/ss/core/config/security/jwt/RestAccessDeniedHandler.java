package com.ss.core.config.security.jwt;

import com.ss.core.exception.SystemErrorTypeEnum;
import com.ss.core.util.ResponseUtil;
import com.ss.core.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Exrickx
 */
@Component
@Slf4j
public class RestAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
        ResponseUtil.out(response, Result.fail(SystemErrorTypeEnum.PERMISSIONS_ERROR));
    }

}
