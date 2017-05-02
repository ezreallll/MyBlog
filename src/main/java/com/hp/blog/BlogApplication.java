package com.hp.blog;

import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.DataSource;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

/**
 * Created by hongpengsun on 17/4/28.
 */
@SpringBootApplication
@MapperScan("sample.mybatis.mapper")
public class BlogApplication {


    @Bean
    @ConfigurationProperties(prefix ="spring.datasource")
    public DataSource dataSource(){
        return new DataSource();
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() throws Exception{
        SqlSessionFactoryBean sqlSessionFactoryBean=new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());
        sqlSessionFactoryBean.setTypeAliasesPackage("com.hp.blog.entity");
        PathMatchingResourcePatternResolver resolver=new PathMatchingResourcePatternResolver();
        sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath*:sqlMappers/*.xml"));
        return sqlSessionFactoryBean.getObject();
    }

    @Bean
    public SqlSessionTemplate sqlWriteSessionTemplate() throws Exception{
        return new SqlSessionTemplate(sqlSessionFactoryBean());
    }

    public static void main(String[] args){
        SpringApplication.run(BlogApplication.class,args);
    }
}
