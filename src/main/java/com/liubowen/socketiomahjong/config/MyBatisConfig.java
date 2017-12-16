package com.liubowen.socketiomahjong.config;

import com.github.pagehelper.PageHelper;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Properties;

/**
 * @author liubowen
 * @date 2017/11/9 21:07
 * @description MyBatis 配置
 */
@Component
public class MyBatisConfig {

    @Autowired
    @Qualifier("defaultDataSource")
    private DataSource defaultDataSource;

    @Bean(name = "defaultSqlSessionFactory")
    public SqlSessionFactory defaultSqlSessionFactory() {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(defaultDataSource);
        // 字段类型转换处理器包设置
        bean.setTypeHandlersPackage("com.liubowen.socketiomahjong.util.mybatis.typeHandler");
        // 数据库实体映射包
        bean.setTypeAliasesPackage("com.liubowen.socketiomahjong.entity");
        // 数据设置
        Configuration configuration = new Configuration();
        // 设置延迟加载
        configuration.setLazyLoadingEnabled(true);
        // 启用下划线与驼峰式命名规则的映射（如first_name => firstName）
        configuration.setMapUnderscoreToCamelCase(true);
        bean.setConfiguration(configuration);

        // 分页插件设置
        PageHelper pageHelper = new PageHelper();
        Properties properties = new Properties();
        properties.setProperty("reasonable", "true");
        properties.setProperty("supportMethodsArguments", "true");
        properties.setProperty("returnPageInfo", "check");
        properties.setProperty("params", "count=countSql");
        pageHelper.setProperties(properties);
        // 添加分页插件
        bean.setPlugins(new Interceptor[] { pageHelper });
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            // 基于注解扫描Mapper，不需配置xml路径
            // bean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return bean.getObject();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
