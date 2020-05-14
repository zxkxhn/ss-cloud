package com.ss.databases.druid.properties;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YamlStatViewServlet {
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