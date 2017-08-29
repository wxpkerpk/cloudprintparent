package com.wx.client.demo;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(DemoApplication.class, args);
    }

//    @Bean
//    public TomcatEmbeddedServletContainerFactory servletContainer(){
//        TomcatEmbeddedServletContainerFactory container = new TomcatEmbeddedServletContainerFactory();
//        container.setPort(8090);
////		container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,""));
//        return container;
//    }

}
