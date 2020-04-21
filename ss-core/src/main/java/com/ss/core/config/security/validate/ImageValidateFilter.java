package com.ss.core.config.security.validate;


import cn.hutool.core.util.StrUtil;
import com.ss.core.config.security.properties.CaptchaProperties;
import com.ss.core.util.ResponseUtil;
import com.ss.core.common.Result;
import com.ss.core.config.security.constant.SecurityConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 图形验证码过滤器
 *
 * @author Exrick
 */
@Slf4j
@Configuration
public class ImageValidateFilter extends OncePerRequestFilter {

    @Resource
    private CaptchaProperties captchaProperties;

    @Resource(name = "stringRedisTemplate")
    private StringRedisTemplate redisTemplate;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {

        // 判断URL是否需要验证
        boolean flag = false;
        String requestUrl = request.getRequestURI();
        PathMatcher pathMatcher = new AntPathMatcher();
        for (String url : captchaProperties.getImage()) {
            if (pathMatcher.match(url, requestUrl)) {
                flag = true;
                break;
            }
        }
        if (flag) {
            String captchaId = request.getParameter("captchaId");
            String code = request.getParameter("code");
            if (StrUtil.isBlank(captchaId) || StrUtil.isBlank(code)) {
                ResponseUtil.out(response, Result.fail("请传入图形验证码所需参数captchaId或code"));
                return;
            }
            String redisCode = redisTemplate.opsForValue().get(SecurityConstant.LOGIN_CAPTCHA_ID + captchaId);
            if (StrUtil.isBlank(redisCode)) {
                ResponseUtil.out(response, Result.fail("验证码已过期，请重新获取"));
                return;
            }

            if (!redisCode.toLowerCase().equals(code.toLowerCase())) {
                log.info("验证码错误：code:{} , redisCode:{}", code, redisCode);
                ResponseUtil.out(response, Result.fail("图形验证码输入错误"));
                return;
            }
            // 已验证清除key
            redisTemplate.delete(SecurityConstant.LOGIN_CAPTCHA_ID + captchaId);
            // 验证成功 放行
            chain.doFilter(request, response);
            return;
        }
        // 无需验证 放行
        chain.doFilter(request, response);
    }
}
