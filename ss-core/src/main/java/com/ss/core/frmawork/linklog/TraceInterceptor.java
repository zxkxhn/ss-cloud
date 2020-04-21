package com.ss.core.frmawork.linklog;

import cn.hutool.core.util.IdUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class TraceInterceptor extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        MDC.put(Constants.LOG_TRACE_ID, IdUtil.fastSimpleUUID());
        log.debug("Http - {} , Method - {}", request.getRequestURI(), request.getMethod());
        filterChain.doFilter(request,response);
    }
}
