package com.wx.cloudprint.imageserver.register;

import com.wx.cloudprint.imageserver.cotroller.ImageController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@SpringBootApplication
@RestController
@EntityScan(value = "com.wx.cloudprint.dataservice.entity")
@ComponentScan({"com.wx.cloudprint"})
@EnableJpaRepositories(basePackages = "com.wx.cloudprint.dataservice.dao")
public class StartImageApplication {

    public static void main(String[] args) {

        if(args.length==0){
            System.out.println("第一个参数请输入本机的ip");
            return;
        }
        ImageController.serverIp=args[0];
        SpringApplication.run(StartImageApplication.class, args);
    }
    private CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        return corsConfiguration;
    }

    /**
     * 跨域过滤器
     * @return
     */
    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig()); // 4
        return new CorsFilter(source);
    }
//    @Bean
//    public TomcatEmbeddedServletContainerFactory servletContainer(){
//        TomcatEmbeddedServletContainerFactory container = new TomcatEmbeddedServletContainerFactory();
//        container.setPort(8090);
////		container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND,""));
//        return container;
//    }

}
