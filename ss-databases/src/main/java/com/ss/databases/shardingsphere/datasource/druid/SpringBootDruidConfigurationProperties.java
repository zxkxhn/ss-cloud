package com.ss.databases.shardingsphere.datasource.druid;


import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ss.druid-cfg", ignoreInvalidFields = true)
@Getter
@Setter
public class SpringBootDruidConfigurationProperties extends YamlDruidConfiguration {

}
