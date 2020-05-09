/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ss.databases.shardingsphere.druid;

import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.pool.DruidDataSource;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.spring.boot.datasource.DataSourcePropertiesSetter;
import org.apache.shardingsphere.spring.boot.util.PropertyUtil;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Hikari datasource properties setter.
 */
@Slf4j
public final class DruidDataSourcePropertiesSetter implements DataSourcePropertiesSetter {

    @Override
    public void propertiesSet(final Environment environment, final String prefix, final String dataSourceName, final DataSource dataSource) {
        Properties properties = new Properties();
        String datasourcePropertiesKey = prefix + dataSourceName.trim() + ".druid-cfg";
        if (PropertyUtil.containPropertyPrefix(environment, datasourcePropertiesKey)) {
            Map<String, Object> datasourceProperties = PropertyUtil.handle(environment, datasourcePropertiesKey, Map.class);
            Map<String, Object> druidDatasourceProperties = new HashMap<>();
            datasourceProperties.forEach((k,v)-> druidDatasourceProperties.put("druid." + StrUtil.toCamelCase(k.replaceAll("-", "_")), v));
            properties.putAll(druidDatasourceProperties);
            DruidDataSource druidDataSource = (DruidDataSource) dataSource;
            druidDataSource.configFromPropety(properties);
        }
    }


    @Override
    public String getType() {
        return "com.alibaba.druid.pool.DruidDataSource";
    }
}
