package com.wx.cloudprint.webserver.register;

import com.wx.cloudprint.webserver.cotroller.WebController;
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
public class StartWebApplication {

    public static void main(String[] args) {


        SpringApplication.run(StartWebApplication.class, args);
    }

}
