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

package com.ss.databases.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.ss.databases.druid.properties.YamlDruidConnPoolProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.spring.boot.datasource.DataSourcePropertiesSetter;
import org.apache.shardingsphere.spring.boot.util.PropertyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

/**
 * Hikari datasource properties setter.
 */
@Slf4j
public final class DruidDataSourcePropertiesSetter implements DataSourcePropertiesSetter {

    @Override
    public void propertiesSet(final Environment environment, final String prefix, final String dataSourceName, final DataSource dataSource) {
        String datasourcePropertiesKey = prefix + dataSourceName.trim() + ".druid-cfg";
        if (PropertyUtil.containPropertyPrefix(environment, datasourcePropertiesKey)) {
            YamlDruidConnPoolProperties datasourceProperties = PropertyUtil.handle(environment, datasourcePropertiesKey, YamlDruidConnPoolProperties.class);
            DruidDataSource druidDataSource = (DruidDataSource) dataSource;
            BeanUtils.copyProperties(datasourceProperties, druidDataSource);
        }
    }


    @Override
    public String getType() {
        return "com.alibaba.druid.pool.DruidDataSource";
    }
}
