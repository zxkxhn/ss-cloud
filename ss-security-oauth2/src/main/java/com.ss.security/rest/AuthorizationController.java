/*
 *  Copyright 2019-2020 Zheng Jie
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.ss.security.rest;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.ss.core.annotation.AnonymousAccess;
import com.ss.core.util.RedisUtils;
import com.ss.security.config.SecurityProperties;
import com.ss.security.security.TokenProvider;
import com.ss.security.service.OnlineUserService;
import com.ss.security.service.dto.AuthUserDto;
import com.ss.security.service.dto.JwtUserDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.undertow.util.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Zheng Jie
 * @date 2018-11-23
 * 授权、根据token获取用户详细信息
 */
@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Api(tags = "系统：系统授权接口")
public class AuthorizationController {

    @Value("${loginCode.expiration}")
    private Long expiration;
    @Value("${single.login:false}")
    private Boolean singleLogin;
    private final SecurityProperties properties;
    private final RedisUtils<String,String> redisUtils;
    private final OnlineUserService onlineUserService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

//    @Log("用户登录")
    @ApiOperation("登录授权")
    @AnonymousAccess
    @PostMapping(value = "/login")
    public ResponseEntity<Object> login(@Validated @RequestBody AuthUserDto authUser, HttpServletRequest request) throws Exception {
        // 密码解密
        String password = new RSA(properties.getPrivateKey()).decryptStr(authUser.getPassword(), KeyType.PrivateKey);
        // 查询验证码
        String code = redisUtils.get(authUser.getUuid());
        // 清除验证码
        redisUtils.delete(authUser.getUuid());
        if (StrUtil.isBlank(code)) {
            throw new BadRequestException("验证码不存在或已过期");
        }
        if (StrUtil.isBlank(authUser.getCode()) || !authUser.getCode().equalsIgnoreCase(code)) {
            throw new BadRequestException("验证码错误");
        }
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(authUser.getUsername(), password);

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // 生成令牌
        String token = tokenProvider.createToken(authentication);
        final JwtUserDto jwtUserDto = (JwtUserDto) authentication.getPrincipal();
        // 保存在线信息
        onlineUserService.save(jwtUserDto, token, request);
        // 返回 token 与 用户信息
        Map<String,Object> authInfo = new HashMap<String,Object>(2){{
            put("token", properties.getTokenStartWith() + token);
            put("user", jwtUserDto);
        }};
        if(singleLogin){
            //踢掉之前已经登录的token
            onlineUserService.checkLoginOnUser(authUser.getUsername(),token);
        }
        return ResponseEntity.ok(authInfo);
    }

//    @ApiOperation("获取用户信息")
//    @GetMapping(value = "/info")
//    public ResponseEntity<Object> getUserInfo(){
//        return ResponseEntity.ok(SecurityUtils.getCurrentUser());
//    }
//
//    @AnonymousAccess
//    @ApiOperation("获取验证码")
//    @GetMapping(value = "/code")
//    public ResponseEntity<Object> getCode(){
//        // 算术类型 https://gitee.com/whvse/EasyCaptcha
//        ArithmeticCaptcha captcha = new ArithmeticCaptcha(111, 36);
//        // 几位数运算，默认是两位
//        captcha.setLen(2);
//        // 获取运算的结果
//        String result = captcha.text();
//        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
//        // 保存
//        redisUtils.set(uuid, result, expiration, TimeUnit.MINUTES);
//        // 验证码信息
//        Map<String,Object> imgResult = new HashMap<String,Object>(2){{
//            put("img", captcha.toBase64());
//            put("uuid", uuid);
//        }};
//        return ResponseEntity.ok(imgResult);
//    }
//
//    @ApiOperation("退出登录")
//    @AnonymousAccess
//    @DeleteMapping(value = "/logout")
//    public ResponseEntity<Object> logout(HttpServletRequest request){
//        onlineUserService.logout(tokenProvider.getToken(request));
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
}
