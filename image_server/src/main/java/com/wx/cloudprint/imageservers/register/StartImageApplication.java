package com.wx.cloudprint.imageservers.register;

import com.weibo.api.motan.common.MotanConstants;
import com.weibo.api.motan.util.MotanSwitcherUtil;
import com.wx.cloudprint.imageservers.cotroller.ImageController;
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
public class StartImageApplication {

    public static void main(String[] args) {

        if(args.length==0){
            System.out.println("第一个参数请输入本机的ip");
            return;
        }
        ImageController.serverIp=args[0];
        SpringApplication.run(StartImageApplication.class, args);
        MotanSwitcherUtil.setSwitcherValue(MotanConstants.REGISTRY_HEARTBEAT_SWITCHER, true);;

    }

}
