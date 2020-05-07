package com.ss.databases.shardingsphere.datasource.druid;

import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.underlying.common.yaml.config.YamlConfiguration;

@Getter
@Setter
public class YamlDruidDataSourceConfiguration implements YamlConfiguration {

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
