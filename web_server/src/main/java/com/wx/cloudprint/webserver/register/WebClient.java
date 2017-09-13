package com.wx.cloudprint.webserver.register;

import com.weibo.api.motan.config.springsupport.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class WebClient {
    @Bean
    public AnnotationBean motanAnnotationBean() {
        AnnotationBean motanAnnotationBean = new AnnotationBean();
        motanAnnotationBean.setPackage("com.wx.cloudprint.webserver.cotroller");
        return motanAnnotationBean;
    }

    @Bean(name = "motan")
    public ProtocolConfigBean protocolConfig() {
        ProtocolConfigBean config = new ProtocolConfigBean();
        config.setDefault(true);
        config.setName("motan");
        config.setMaxContentLength(1048571600);
        config.setRequestTimeout(800000);
        return config;
    }

    @Bean(name = "registry")
    public RegistryConfigBean registryConfig() {
        RegistryConfigBean config = new RegistryConfigBean();
        config.setRegProtocol("zookeeper");
        config.setAddress("139.199.202.40:2181");
        return config;
    }

    @Bean(name = "basicRefererConfig")
    public BasicRefererConfigBean basicRefererConfigBean() {
        BasicRefererConfigBean config = new BasicRefererConfigBean();
        config.setProtocol("motan");
        config.setRegistry("registry");
        config.setThrowException(true);
        return config;
    }


}
 