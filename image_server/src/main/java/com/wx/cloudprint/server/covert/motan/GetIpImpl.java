package com.wx.cloudprint.server.covert.motan;

import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import com.wx.cloudprint.imageservers.cotroller.ImageController;
import org.springframework.beans.factory.annotation.Value;

@MotanService
public class GetIpImpl implements GetIp {
    @Value("${server.port}")
    String port;
    @Override
    public String getIp() {
        return ImageController.genRestfulUrl(ImageController.serverIp,port,"/API/file/upload");
    }
}
