package com.ss.security.service;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * 权限接口
 *
 * @author Exrick
 */
public interface SecurityService {


    /**
     * 获取用户信息
     * @param username 用户名
     */
    UserDetails getUserDetail(String username);


    /**
     * 获取用户所有权限
     * @param username
     * @return
     */
    List<GrantedAuthority> getCurrUserPerms(String username);


    void clearUserToken(String username);



}
