package com.ss.databases.shardingsphere.datasource;


import lombok.Getter;
import lombok.Setter;
import org.apache.shardingsphere.underlying.common.yaml.config.YamlConfiguration;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 基本的数据库配置连接
 */
@Getter
@Setter
public class YamlDataSourceConfiguration implements YamlConfiguration {

    private Class<? extends DataSource> type;

    private String driverClassName;

    private String url;

    private String username;

    private String password;

    private String jndiName;


    public Map<String, Object> toMap (){
        Map<String, Object> map = new HashMap<>();
        map.put("driverClassName", this.driverClassName);
        map.put("type", this.type.getName());
        map.put("url", this.url);
        map.put("username", this.username);
        map.put("password", this.password);
        map.put("jndiName", this.jndiName);
        return map;
    }
}
