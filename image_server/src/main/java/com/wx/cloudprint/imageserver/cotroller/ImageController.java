package com.wx.cloudprint.imageserver.cotroller;

import com.wx.cloudprint.dataservice.entity.Res;
import com.wx.cloudprint.dataservice.service.ResService;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.wx.cloudprint.imageserver.service.ImageService;

import com.wx.cloudprint.server.covert.motan.WEP2PDF;
import message.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import util.FileUtils;
import util.MD5Util;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/API")
public class ImageController {

    @MotanReferer(basicReferer = "basicRefererConfig")
    private WEP2PDF wep2PDF;

    @Value("${file.path}")
    String filePath;

    @Autowired
    private ImageService imageService;


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

    public static String genRestfulUrl(String ip,String port,String prefix)
    {
        StringBuilder builder=new StringBuilder(80);
        builder.append("http://").append(ip).append(":").append(port).append(prefix);
        return builder.toString();
    }
    static String getFilePrefix(String name){
        String []temp=name.split("\\.");
        return temp.length>=2 ?temp[temp.length-1]:null;
    }
    @RequestMapping(value = "file/upload", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Message upload(@RequestParam MultipartFile file) throws IOException {
        byte[] data = file.getBytes();
        String name = file.getOriginalFilename();
        String prefix = getFilePrefix(name);
        String currentMD5 = MD5Util.encrpt(data);
        boolean isDerect = false;
        //调用远程服务群
        byte imageBytes[][] = wep2PDF.offceBytes2imgsBytes(data, prefix);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes[0]);
        BufferedImage image = ImageIO.read(byteArrayInputStream);
        int width = image.getWidth();
        int height = image.getHeight();
        if (height > width) isDerect = true;

        File parentFile = new File(filePath, currentMD5);
        parentFile.mkdir();
        for (int i = 0; i < imageBytes.length; i++) {
            File f = new File(parentFile.getPath(), i + ".png");
            FileUtils.writeByte(f.getPath(), imageBytes[i]);
        }
        Res res = new Res();
        res.setHost(serverIp);
        res.setPort(port);
        res.setDirection(isDerect);
        res.setMd5(currentMD5);
        res.setName(name);
        res.setTime(new Date());
        res.setPage(imageBytes.length);
        resService.add(res);
        return Message.createMessage(Message.success_state, currentMD5);

    }


    @RequestMapping(value = "file/pic/get/preview", method = {RequestMethod.GET, RequestMethod.POST})
    public void getPreview(HttpServletRequest request, HttpServletResponse response, @RequestParam String md5, @RequestParam String size, @RequestParam int row, @RequestParam int col, @RequestParam(required = false) Integer page
            , @RequestParam(required = false) Boolean isMono) throws IOException {


        isMono = false;
        String sourcePath = new File(filePath, md5).getPath();
        Res res=resService.getByMD5(md5);
        if(res!=null) {

            byte[][] result = imageService.combineImg(sourcePath, row, col, size, --page, res.getDirection(), isMono);
            response.setContentType("image/png");
            OutputStream outputStream = response.getOutputStream();
            if(result!=null)
            outputStream.write(result[0]);
        }


    }



}
