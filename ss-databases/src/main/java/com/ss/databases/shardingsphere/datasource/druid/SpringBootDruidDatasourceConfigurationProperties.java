package com.ss.databases.shardingsphere.datasource.druid;

import com.ss.databases.shardingsphere.datasource.BaseSpringBootDatasourceConfigurationProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;


@ConfigurationProperties(prefix = "ss", ignoreInvalidFields = true)
@Getter
@Setter
public class SpringBootDruidDatasourceConfigurationProperties extends BaseSpringBootDatasourceConfigurationProperties {

    Map<String, YamlDruidDataSourceConfiguration> druidCfg;
}


