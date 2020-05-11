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
package com.ss.databases.shardingsphere.druid.properties;

import lombok.Getter;
import lombok.Setter;

/**
 * @author lihengming [89921218@qq.com]
 */
@Getter
@Setter
public class YamlDruidStatProperties {

    private String[] aopPatterns;
    private StatViewServlet statViewServlet = new StatViewServlet();
    private WebStatFilter webStatFilter = new WebStatFilter();


    @Getter
    @Setter
    public static class StatViewServlet {
        /**
         * Enable StatViewServlet, default false.
         */
        private boolean enabled;
        private String urlPattern;
        private String allow;
        private String deny;
        private String loginUsername;
        private String loginPassword;
        private String resetEnable;
    }

    @Getter
    @Setter
    public static class WebStatFilter {
        /**
         * Enable WebStatFilter, default false.
         */
        private boolean enabled;
        private String urlPattern;
        private String exclusions;
        private String sessionStatMaxCount;
        private String sessionStatEnable;
        private String principalSessionName;
        private String principalCookieName;
        private String profileEnable;
    }
}
