package com.ss.core.config;


import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;


/**
 * mybatis plus 装配
 *
 * @author xiaokun.zhang
 * Date:   2019年11月19日 15:56
 * @version 1.0
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.zxk.admin.biz.dao")
public class MybatisPlusConfig {

    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
        // 设置最大单页限制数量，默认 50 条，-1 不受限制
        paginationInterceptor.setLimit(50);
        return paginationInterceptor;
    }


}
