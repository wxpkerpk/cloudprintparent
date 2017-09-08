package com.wx.cloudprint.cotroller;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.wx.cloudprint.Service.ImageService;
import com.wx.cloudprint.message.Message;
import com.wx.cloudprint.util.MD5Util;
import com.wx.cloudprint.server.covert.motan.WEP2PDF;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.wx.cloudprint.util.FileUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

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
        String currentMD5= MD5Util.encrpt(data);
        if(md5.equals(currentMD5)){
          byte imageBytes[][]=  wep2PDF.offceBytes2imgsBytes(data,prefix);
          File parentFile=new File(filePath,md5);
          for(int i=0;i<imageBytes.length;i++){
              File f=new File(parentFile.getPath(),i+".png");
              FileUtils.writeByte(f.getPath(),imageBytes[i]);
          }
          return Message.createMessage(Message.success_state,null);
        }
        return Message.createMessage(Message.fail_state,null);

    }

    @RequestMapping(value = "/pic/preview",method = {RequestMethod.GET,RequestMethod.POST})
    public void preview(HttpServletRequest request, HttpServletResponse response,@RequestParam String md5, @RequestParam String size,@RequestParam int row,@RequestParam int col,@RequestParam(required = false)Integer page
            ,@RequestParam boolean isMono) throws IOException {


        String sourcePath=new File(filePath,md5).getPath();
        byte [][]result= imageService.combineImg(sourcePath,row,col,size,page,false,isMono);
        response.setContentType("image/png");
        try {
            response.setHeader("Content-Disposition",
                    "attachment;filename=" +  new String((md5+"_"+page).getBytes(), "iso8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        OutputStream outputStream= response.getOutputStream();
        outputStream.write(result[0]);


    }

}
