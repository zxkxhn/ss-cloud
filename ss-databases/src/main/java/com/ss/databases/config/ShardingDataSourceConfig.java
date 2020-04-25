package com.ss.databases.config;

import cn.hutool.core.io.IoUtil;
import com.alibaba.druid.filter.Filter;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.wall.WallConfig;
import com.alibaba.druid.wall.WallFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.core.yaml.config.sharding.YamlRootShardingConfiguration;
import org.apache.shardingsphere.core.yaml.engine.YamlEngine;
import org.apache.shardingsphere.core.yaml.swapper.impl.ShardingRuleConfigurationYamlSwapper;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 分库分表Sharding-jdbc 装配
 *
 * @author xiaokun.zhang
 * Date:   2019年11月25日 15:20
 * @version 1.0
 */


@Slf4j
@Configuration
public class ShardingDataSourceConfig {

    @Resource
    private Filter statFilter;


    private static final String SHARDING_SPHERE_CONFIG_FILE = "./dataSource.yml";


    @Bean
    public DataSource dataSource() throws SQLException, IOException {
        YamlRootShardingConfiguration config = parse();
        Map<String, DataSource> dataSources = config.getDataSources();
        dataSources.forEach((k, v) -> {
            DruidDataSource d = (DruidDataSource) v;
            List<Filter> filters = new ArrayList<>();
            filters.add(statFilter);

            d.setInitialSize(10);
            d.setMaxActive(100);
            d.setMinIdle(10);
            d.setMaxWait(60000);
            d.setPoolPreparedStatements(true);
            d.setMaxPoolPreparedStatementPerConnectionSize(1);
            d.setTimeBetweenEvictionRunsMillis(60000);
            d.setMinEvictableIdleTimeMillis(300000);
            d.setValidationQuery("SELECT X");
            d.setValidationQueryTimeout(5000);
            d.setTestWhileIdle(true);
            d.setTestOnBorrow(false);

            try {
                d.setFilters("stat,wall,log4j2");
            } catch (SQLException e) {
                log.error("druid configuration initialization filter", e);
            }


            WallConfig wallConfig = new WallConfig();
            wallConfig.setMultiStatementAllow(true);
            WallFilter wallFilter = new WallFilter();
            wallFilter.setConfig(wallConfig);
            filters.add(wallFilter);
            d.setProxyFilters(filters);
        });
        return ShardingDataSourceFactory.createDataSource(dataSources, new ShardingRuleConfigurationYamlSwapper().swap(config.getShardingRule()), config.getProps());
    }

    /**
     * 解析yml
     */
    private YamlRootShardingConfiguration parse() throws IOException {
        YamlRootShardingConfiguration yamlRootShardingConfiguration;
        ClassPathResource resource = new ClassPathResource(SHARDING_SPHERE_CONFIG_FILE);
        try (InputStream inputStream = resource.getInputStream()) {
            yamlRootShardingConfiguration = YamlEngine.unmarshal(IoUtil.read(inputStream).toByteArray(), YamlRootShardingConfiguration.class);
        }
        if (yamlRootShardingConfiguration == null) {
            throw new NullPointerException("dataSource load error");
        }
        return yamlRootShardingConfiguration;
    }
}
