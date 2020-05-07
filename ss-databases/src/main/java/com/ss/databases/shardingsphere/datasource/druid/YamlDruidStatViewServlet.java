package com.ss.databases.shardingsphere.datasource.druid;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class YamlDruidStatViewServlet {
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