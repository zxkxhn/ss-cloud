/*
 * Copyright 1999-2018 Alibaba Group Holding Ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ss.databases.shardingsphere.datasource.druid;

import com.alibaba.druid.support.http.StatViewServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author lihengming [89921218@qq.com]
 */
@ConditionalOnWebApplication
@ConditionalOnProperty(name = "ss.druid-cfg.stat-view-servlet.enabled", havingValue = "true")
public class DruidStatViewServletConfiguration {

    @Autowired
    private SpringBootDruidConfigurationProperties druidConfigurationProperties;


    private static final String DEFAULT_ALLOW_IP = "127.0.0.1";

    @Bean
    public ServletRegistrationBean<StatViewServlet> statViewServletRegistrationBean() {
         YamlDruidStatViewServlet properties = druidConfigurationProperties.getStatViewServlet();
        ServletRegistrationBean<StatViewServlet> registrationBean = new ServletRegistrationBean<>();
        registrationBean.setServlet(new StatViewServlet());
        registrationBean.addUrlMappings(properties.getUrlPattern() != null ? properties.getUrlPattern() : "/druid/*");
        if (properties.getAllow() != null) {
            registrationBean.addInitParameter("allow", properties.getAllow());
        } else {
            registrationBean.addInitParameter("allow", DEFAULT_ALLOW_IP);
        }
        if (properties.getDeny() != null) {
            registrationBean.addInitParameter("deny", properties.getDeny());
        }
        if (properties.getLoginUsername() != null) {
            registrationBean.addInitParameter("loginUsername", properties.getLoginUsername());
        }
        if (properties.getLoginPassword() != null) {
            registrationBean.addInitParameter("loginPassword", properties.getLoginPassword());
        }
        if (properties.getResetEnable() != null) {
            registrationBean.addInitParameter("resetEnable", properties.getResetEnable());
        }
        return registrationBean;
    }
}
