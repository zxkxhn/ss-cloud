package com.ss.databases.druid.properties;

import com.alibaba.druid.pool.DruidAbstractDataSource;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * druid 连接池配置
 */
@Getter
@Setter
public class YamlDruidConnPoolProperties {

    /**
     * 初始化时建立物理连接的个数。初始化发生在显示调用init方法，或者第一次getConnection时
     */
    private Integer initialSize = DruidAbstractDataSource.DEFAULT_INITIAL_SIZE;

    /**
     * 最大连接池数量
     */
    private Integer maxActive = DruidAbstractDataSource.DEFAULT_MAX_ACTIVE_SIZE;

    /**
     * 最小连接池数量
     */
    private Integer minIdle = DruidAbstractDataSource.DEFAULT_MIN_IDLE;

    /**
     * 已经不再使用，配置了也没效果
     */
//    @Deprecated
//    private Integer maxIdle = DruidAbstractDataSource.DEFAULT_MAX_IDLE;

    /**
     * 获取连接时最大等待时间，单位毫秒。配置了maxWait之后，缺省启用公平锁，
     * 并发效率会有所下降，如果需要可以通过配置useUnfairLock属性为true使用非公平锁。
     */
    private Integer maxWait = DruidAbstractDataSource.DEFAULT_MAX_WAIT;

    /**
     * 是否缓存preparedStatement，也就是PSCache。PSCache对支持游标的数据库性能提升巨大，比如说oracle。在mysql下建议关闭。
     */
    private Boolean poolPreparedStatements = false;

    /**
     * 要启用PSCache，必须配置大于0，当大于0时，poolPreparedStatements自动触发修改为true。
     * 在Druid中，不会存在Oracle下PSCache占用内存过多的问题，可以把这个数值配置大一些，比如说100
     */
    private Integer maxPoolPreparedStatementPerConnectionSize = 10;

    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句，常用select 'x'。
     * 如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
     */
    private String validationQuery = DruidAbstractDataSource.DEFAULT_VALIDATION_QUERY;

    /**
     * 单位：秒，检测连接是否有效的超时时间。底层调用jdbc Statement对象的void setQueryTimeout(int seconds)方法
     */
    private Integer validationQueryTimeout = -1;

    /**
     * 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能
     */
    private Boolean testOnBorrow = true;

    /**
     * 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    private Boolean testOnReturn = false;

    /**
     * 建议配置为true，不影响性能，并且保证安全性。
     * 申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。
     */
    private Boolean testWhileIdle = false;

    /**
     * 有两个含义：
     * 1) Destroy线程会检测连接的间隔时间，如果连接空闲时间大于等于minEvictableIdleTimeMillis则关闭物理连接。
     * 2) testWhileIdle的判断依据，详细看testWhileIdle属性的说明
     */
    private Long timeBetweenEvictionRunsMillis = DruidAbstractDataSource.DEFAULT_TIME_BETWEEN_EVICTION_RUNS_MILLIS;

    /**
     * 不再使用，一个DruidDataSource只支持一个EvictionRun
     */
    @Deprecated
    private Integer numTestsPerEvictionRun = DruidAbstractDataSource.DEFAULT_NUM_TESTS_PER_EVICTION_RUN;

    /**
     * 连接保持空闲而不被驱逐的最小时间
     */
    private Long minEvictableIdleTimeMillis = DruidAbstractDataSource.DEFAULT_MIN_EVICTABLE_IDLE_TIME_MILLIS;

    /**
     * 属性类型是字符串，通过别名的方式配置扩展插件，常用的插件有：
     * 监控统计用的filter:stat
     * 日志用的filter:log4j
     * 防御sql注入的filter:wall
     */
    private List<String> filters;

}
