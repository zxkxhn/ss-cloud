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

package com.ss.databases.shardingsphere;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.ss.databases.shardingsphere.common.SpringBootPropertiesConfigurationProperties;
import com.ss.databases.shardingsphere.encrypt.EncryptRuleCondition;
import com.ss.databases.shardingsphere.encrypt.SpringBootEncryptRuleConfigurationProperties;
import com.ss.databases.shardingsphere.masterslave.MasterSlaveRuleCondition;
import com.ss.databases.shardingsphere.masterslave.SpringBootMasterSlaveRuleConfigurationProperties;
import com.ss.databases.shardingsphere.sharding.ShardingRuleCondition;
import com.ss.databases.shardingsphere.sharding.SpringBootShardingRuleConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.apache.shardingsphere.core.config.inline.InlineExpressionParser;
import org.apache.shardingsphere.core.exception.ShardingException;
import org.apache.shardingsphere.core.yaml.swapper.impl.EncryptRuleConfigurationYamlSwapper;
import org.apache.shardingsphere.core.yaml.swapper.impl.MasterSlaveRuleConfigurationYamlSwapper;
import org.apache.shardingsphere.core.yaml.swapper.impl.ShardingRuleConfigurationYamlSwapper;
import org.apache.shardingsphere.shardingjdbc.api.EncryptDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.api.MasterSlaveDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.spring.boot.datasource.DataSourcePropertiesSetter;
import org.apache.shardingsphere.spring.boot.datasource.DataSourcePropertiesSetterHolder;
import org.apache.shardingsphere.spring.boot.util.DataSourceUtil;
import org.apache.shardingsphere.spring.boot.util.PropertyUtil;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.StandardEnvironment;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Spring boot starter configuration.
 *
 * @author caohao
 * @author panjuan
 * @author yangyi
 */
@Configuration
@ComponentScan("org.apache.shardingsphere.spring.boot.converter")
@EnableConfigurationProperties({
        SpringBootShardingRuleConfigurationProperties.class,
        SpringBootMasterSlaveRuleConfigurationProperties.class,
        SpringBootEncryptRuleConfigurationProperties.class,
        SpringBootPropertiesConfigurationProperties.class
})
@ConditionalOnProperty(prefix = "ss.datasource", name = "enabled", havingValue = "true", matchIfMissing = true)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
@RequiredArgsConstructor
public class SpringBootConfiguration implements EnvironmentAware {
    
    private final SpringBootShardingRuleConfigurationProperties shardingRule;
    
    private final SpringBootMasterSlaveRuleConfigurationProperties masterSlaveRule;
    
    private final SpringBootEncryptRuleConfigurationProperties encryptRule;
    
    private final SpringBootPropertiesConfigurationProperties props;
    
    private final Map<String, DataSource> dataSourceMap = new LinkedHashMap<>();

    private final String jndiName = "jndi-name";

    /**
     * Get sharding data source bean.
     *
     * @return data source bean
     * @throws SQLException SQL exception
     */
    @Bean
    @Conditional(ShardingRuleCondition.class)
    public DataSource shardingDataSource() throws SQLException {
        return ShardingDataSourceFactory.createDataSource(dataSourceMap, new ShardingRuleConfigurationYamlSwapper().swap(shardingRule), props.getProps());
    }
    
    /**
     * Get master-slave data source bean.
     *
     * @return data source bean
     * @throws SQLException SQL exception
     */
    @Bean
    @Conditional(MasterSlaveRuleCondition.class)
    public DataSource masterSlaveDataSource() throws SQLException {
        return MasterSlaveDataSourceFactory.createDataSource(dataSourceMap, new MasterSlaveRuleConfigurationYamlSwapper().swap(masterSlaveRule), props.getProps());
    }
    
    /**
     * Get encrypt data source bean.
     *
     * @return data source bean
     * @throws SQLException SQL exception
     */
    @Bean
    @Conditional(EncryptRuleCondition.class)
    public DataSource encryptDataSource() throws SQLException {
        return EncryptDataSourceFactory.createDataSource(dataSourceMap.values().iterator().next(), new EncryptRuleConfigurationYamlSwapper().swap(encryptRule), props.getProps());
    }
    
    @Override
    public final void setEnvironment(final Environment environment) {
        String prefix = "ss.datasource.";
        for (String each : getDataSourceNames(environment, prefix)) {
            try {
                dataSourceMap.put(each, getDataSource(environment, prefix, each));
            } catch (final ReflectiveOperationException ex) {
                throw new ShardingException("Can't find datasource type!", ex);
            } catch (final NamingException namingEx) {
                throw new ShardingException("Can't find JNDI datasource!", namingEx);
            }
        }
    }
    
    private List<String> getDataSourceNames(final Environment environment, final String prefix) {
        StandardEnvironment standardEnv = (StandardEnvironment) environment;
        standardEnv.setIgnoreUnresolvableNestedPlaceholders(true);
        return null == standardEnv.getProperty(prefix + "name") 
                ? new InlineExpressionParser(standardEnv.getProperty(prefix + "names")).splitAndEvaluate()
                : Collections.singletonList(standardEnv.getProperty(prefix + "name"));
    }
    
    @SuppressWarnings("unchecked")
    private DataSource getDataSource(final Environment environment, final String prefix, final String dataSourceName) throws ReflectiveOperationException, NamingException {
        Map<String, Object> dataSourceProps = PropertyUtil.handle(environment, prefix + dataSourceName.trim(), Map.class);
        Preconditions.checkState(!dataSourceProps.isEmpty(), "Wrong datasource properties!");
        if (dataSourceProps.containsKey(jndiName)) {
            return getJndiDataSource(dataSourceProps.get(jndiName).toString());
        }
        DataSource result = DataSourceUtil.getDataSource(dataSourceProps.get("type").toString(), dataSourceProps);
        Optional<DataSourcePropertiesSetter> dataSourcePropertiesSetter = DataSourcePropertiesSetterHolder.getDataSourcePropertiesSetterByType(dataSourceProps.get("type").toString());
        if (dataSourcePropertiesSetter.isPresent()) {
            dataSourcePropertiesSetter.get().propertiesSet(environment, prefix, dataSourceName, result);
        }
        return result;
    }
    
    private DataSource getJndiDataSource(final String jndiName) throws NamingException {
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setResourceRef(true);
        bean.setJndiName(jndiName);
        bean.setProxyInterface(DataSource.class);
        bean.afterPropertiesSet();
        return (DataSource) bean.getObject();
    }
}
