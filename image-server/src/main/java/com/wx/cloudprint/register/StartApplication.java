package com.wx.cloudprint.register;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StartApplication {

    public static void main(String[] args) {

        SpringApplication.run(StartApplication.class, args);
    }

//    @Bean
//    public TomcatEmbeddedServletContainerFactory servletContainer(){
//        TomcatEmbeddedServletContainerFactory container = new TomcatEmbeddedServletContainerFactory();
//        container.setPort(8090);
////		container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,""));
//        return container;
//    }

}
