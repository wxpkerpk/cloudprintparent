package com.wx.cloudprint.notifyserver.alilpay.register;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EntityScan(value = "com.wx.cloudprint.dataservice.entity")
@ComponentScan({"com.wx.cloudprint"})
@EnableJpaRepositories(basePackages = "com.wx.cloudprint.dataservice.dao")
public class StartNotifyApplication {

    public static void main(String[] args) {

        SpringApplication.run(StartNotifyApplication.class, args);

    }

}
