package com.ss.databases.shardingsphere.datasource.druid;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YamlDruidWebStatFilter {
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