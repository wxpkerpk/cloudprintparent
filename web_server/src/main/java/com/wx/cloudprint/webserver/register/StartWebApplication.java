package com.wx.cloudprint.webserver.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

@SpringBootApplication
@EntityScan(value = "com.wx.cloudprint.dataservice.entity")
@ComponentScan({"com.wx.cloudprint"})
@EnableJpaRepositories(basePackages = "com.wx.cloudprint.dataservice.dao")
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600*12)
public class StartWebApplication {

    public static void main(String[] args) {


        SpringApplication.run(StartWebApplication.class, args);
    }
    @Bean
    public HibernateJpaSessionFactoryBean sessionFactory() {
        return new HibernateJpaSessionFactoryBean();
    }

}
