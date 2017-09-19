package com.wx.cloudprint.webserver.cotroller;

import com.wx.cloudprint.dataservice.entity.Res;
import com.wx.cloudprint.dataservice.service.ResService;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;

import com.wx.cloudprint.server.covert.motan.GetIp;
import message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import util.FileUtils;
import util.MD5Util;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/API")
public class WebController {

//    @MotanReferer(basicReferer = "basicRefererConfig")
    private GetIp getIp;

    @Value("${file.path}")
    String filePath;




    @Value("${server.port}")
    String port;
    @Resource
    private ResService resService;

    class Url implements Serializable {
        public String url;

    }

    public static String serverIp;


    public static void main(String s[]) {
        byte data[] = FileUtils.readByte("C:\\Users\\wx\\Documents\\工作簿1.xlsx");
        String md5 = MD5Util.encrpt(data);
        System.out.println(md5);
    }

    @RequestMapping(value = "file/url", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Message url() {
        Url url = new Url();
        url.url = getIp.getIp();
        Message message = Message.createMessage(Message.success_state, url);
        return message;
    }
    private String genRestfulUrl(String ip,String port,String prefix)
    {
        StringBuilder builder=new StringBuilder(80);
        builder.append("http://").append(ip).append(":").append(port).append(prefix);
        return builder.toString();
    }

    static String getFilePrefix(String name){
        String []temp=name.split("\\.");
        return temp.length>=2 ?temp[temp.length-1]:null;
    }


    @RequestMapping(value = "file/pic/preview", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Message Preview(HttpServletRequest request, HttpServletResponse response, @RequestParam String md5, @RequestParam String size, @RequestParam int row, @RequestParam int col, @RequestParam(required = false) Integer page
            , @RequestParam(required = false) Boolean isMono) {

        Res res=resService.getByMD5(md5);
        if(res!=null) {
            String url = genRestfulUrl(res.getHost(), res.getPort(), "/API/file/pic/get/preview?" + String.format("md5=%s&size=%s&row=%s&col=%s&page=%s", md5, size, row, col, page));

            Map<String, String> map = new LinkedHashMap<>();
            map.put("img", url);
            return Message.createMessage(Message.success_state, map);
        }
        return null;
    }


    @RequestMapping(value = "file/page", method = {RequestMethod.GET, RequestMethod.POST})
    public Map<String, Object> getPage(@RequestParam String md5) {
        Res res = resService.getByMD5(md5);
        Map<String, Object> message = new LinkedHashMap<>();

        if (res != null) {
            message.put("result", "EXISTED");
            message.put("pageCount", res.getPage());
            message.put("direction", res.getDirection());
        } else {
            message.put("result", "NO_EXIST");
        }
        return message;
    }


}
