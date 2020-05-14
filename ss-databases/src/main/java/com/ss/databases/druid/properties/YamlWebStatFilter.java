package com.ss.databases.druid.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YamlWebStatFilter {
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