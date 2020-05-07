package com.ss.databases.shardingsphere.datasource.druid;

import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.underlying.common.yaml.config.YamlConfiguration;

@Getter
@Setter
public class YamlDruidConfiguration implements YamlConfiguration {

    private String[] aopPatterns;

    private YamlDruidStatViewServlet statViewServlet;

    private YamlDruidWebStatFilter webStatFilter;

}
