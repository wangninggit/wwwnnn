package com.viathink.api.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.viathink.core.util.myBatisConfigUtil;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;


@Configuration
@PropertySource("classpath:application.properties")
public class MyBatisConfig {
    private static Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);
    private final Environment env;

    @Autowired
    public MyBatisConfig(Environment env) {
        this.env = env;
    }

    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druidDataSource() {
        return new DruidDataSource();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource)
            throws Exception {
        return myBatisConfigUtil.sqlSessionFactory(dataSource,env,logger);
    }
}
