package com.liubowen.socketiomahjong.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

/**
 * @author liubowen
 * @date 2017/11/9 20:03
 * @description 数据源配置
 */
@Component
public class DataSourceConfig {

    @Bean(name = "defaultDataSource")
    @Qualifier("defaultDataSource")
    @ConfigurationProperties(prefix="spring.datasource.default")
    public DataSource defaultDataSource() {
        return DataSourceBuilder.create().build();
    }
}
