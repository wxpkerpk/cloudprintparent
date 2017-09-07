package com.wx.cloudprint.cotroller;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.wx.cloudprint.Service.ImageService;
import com.wx.cloudprint.message.Message;
import com.wx.cloudprint.message.utils.MD5Util;
import com.wx.cloudprint.server.covert.motan.WEP2PDF;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import utils.FileUtils;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

@RestController
@RequestMapping(name = "/API/file")
public class ImageController {

    @MotanReferer(basicReferer = "basicRefererConfig")
    private WEP2PDF wep2PDF;

    @Value("${file.path}")
    String filePath;

    @Resource
    private ImageService imageService;
    class Url implements Serializable{
        String host;

        public Url setHost(String host) {
            this.host = host;
            return this;
        }
    }
    @Value("${server.ip}")
    String serverIp;
    @RequestMapping(value = "url",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Message url() {
        Url url=new Url();
        url.host=serverIp;
        return Message.createMessage(Message.success_state,url);
    }
    @RequestMapping(value = "upload",method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public Message upload(@RequestParam MultipartFile file, @RequestParam String md5) throws IOException {
        byte[]data= file.getBytes();
        String name=file.getName();
        String prefix=name.split("\\.")[1];
        name=name.split("\\.")[0];
        String currentMD5= MD5Util.encrpt(data);
        if(md5.equals(currentMD5)){
          byte imageBytes[][]=  wep2PDF.offceBytes2imgsBytes(data);
          File parentFile=new File(filePath,md5);
          for(int i=0;i<imageBytes.length;i++){
              File f=new File(parentFile.getPath(),i+".png");
              FileUtils.writeFile(imageBytes[i],f.getPath());
          }
          return Message.createMessage(Message.success_state,null);
        }
        return Message.createMessage(Message.fail_state,null);


    }
}
