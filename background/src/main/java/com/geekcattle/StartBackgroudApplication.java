/*
 * Copyright (c) 2017 <l_iupeiyu@qq.com> All rights reserved.
 */

package com.geekcattle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import javax.sql.DataSource;

@SpringBootApplication
@EntityScan(value = "com.wx.cloudprint.dataservice.entity")
@EnableJpaRepositories(basePackages = "com.wx.cloudprint.dataservice.dao")
@EnableWebMvc//启动MVC
@ComponentScan({"com.wx.cloudprint", "com.geekcattle"})
@EnableTransactionManagement // 启注解事务管理，等同于xml配置方式的 <tx:annotation-driven />
public class StartBackgroudApplication extends WebMvcConfigurerAdapter {
    @Resource(name = "transactionManager")
    private PlatformTransactionManager txManager2;
    /**
     * 如果要发布到自己的Tomcat中的时候，需要继承SpringBootServletInitializer类，并且增加如下的configure方法。
     * 如果不发布到自己的Tomcat中的时候，就无需上述的步骤
     */
   /* @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.clas);
    }*/

    public static void main(String[] args){
        SpringApplication.run(StartBackgroudApplication.class, args);
    }

    @Bean(name = "transactionManager")
    public PlatformTransactionManager txManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }


//    @Bean
//    public Object testBean(PlatformTransactionManager platformTransactionManager) {
//        System.out.println(">>>>>>>>>>" + platformTransactionManager.getClass().getName());
//        return new Object();
//    }

}
