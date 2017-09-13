package com.wx.cloudprint.webserver.cotroller;

import com.wx.cloudprint.dataservice.entity.Res;
import com.wx.cloudprint.dataservice.service.ResService;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;

import com.wx.cloudprint.server.covert.motan.GetIp;
import message.Message;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import util.FileUtils;
import util.MD5Util;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/API")
public class WebController {

    @MotanReferer(basicReferer = "basicRefererConfig")
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
    @RequestMapping(value = "point/address", method = {RequestMethod.GET, RequestMethod.POST})
    public Message getAddress(){

        String content="{\"result\":\"OK\",\"info\":{\"上海市\":{\"市辖区\":[\"闸北区\",\"和布克赛尔蒙古自治县\",\"静安区\",\"黔西县\",\"丰润区\",\"虹口区\",\"三地门乡\"],\"县\":[\"赫章县\",\"灌南县\",\"-\",\"芒市\"]},\"北京市\":{\"市辖区\":[\"仲巴县\",\"武清区\",\"越秀区\",\"屯门区\",\"其它区\",\"望城区\",\"彭泽县\"],\"县\":[\"朝天区\",\"海州区\",\"玉门市\",\"含山县\",\"-\"]}}}";
        return Message.createMessage(Message.success_state,content);

    }
    @RequestMapping(value = "point/points", method = {RequestMethod.GET, RequestMethod.POST})
    public Message getPoints(){

        String content="{\"result\":\"OK\",\"info\":[{\"pointID\":95,\"status\":\"RUNNING\",\"pointType\":\"ATM\",\"delivery_scope\":\"\",\"delivery_time\":\"每天中午十二点到两点，每天晚上8点到10点\",\"phone\":\"13845426158\",\"pointName\":\"巴青县大楼\",\"address\":\"软件园华北方向直走\",\"message\":\"欢迎使用快印打印~\",\"image\":\"\",\"takeTime\":[22,32,24,0],\"price\":{\"A4\":{\"70g\":{\"mono\":{\"duplex\":46},\"colorful\":{\"duplex\":30,\"oneside\":39}},\"80g\":{\"mono\":{\"duplex\":49},\"colorful\":{\"oneside\":38,\"duplex\":48}}},\"A3\":{\"70g\":{\"mono\":{\"oneside\":19}}}},\"printInfo\":{\"maxPageCount\":200,\"prePrintStart\":500,\"distributionStart\":1000,\"distributionCharge_free\":300,\"distributionCharge\":100},\"preInfo\":{\"maxPageCount\":500,\"prePrintStart\":500,\"desc\":\"\"},\"atmInfo\":{\"maxPageCount\":200,\"desc\":\"\"},\"dispatch\":{\"maxPageCount\":500,\"distributionStart\":500,\"distributionCharge_free\":300,\"distributionCharge\":200,\"desc\":\"\"},\"minCharge\":0,\"reduction\":{\"withCoupon\":false,\"newUser\":0,\"full\":[],\"redPacket\":false,\"activity\":[]}},{\"pointID\":96,\"status\":\"SUMMER_HOLIDAY\",\"pointType\":\"DISPATCHING\",\"delivery_scope\":\"\",\"delivery_time\":\"每天中午十二点到两点，每天晚上8点到10点\",\"phone\":\"13232314384\",\"pointName\":\"霞山区大楼\",\"address\":\"Paul Wilson一楼进大门左转\",\"message\":\"欢迎使用快印打印~\",\"image\":\"\",\"takeTime\":[14,25,24,0],\"price\":{\"A4\":{\"70g\":{\"mono\":{\"duplex\":42,\"oneside\":15},\"colorful\":{\"duplex\":45,\"oneside\":29}}},\"A3\":{\"70g\":{\"mono\":{\"duplex\":14}}}},\"printInfo\":{\"maxPageCount\":200,\"prePrintStart\":500,\"distributionStart\":1000,\"distributionCharge_free\":300,\"distributionCharge\":100},\"preInfo\":{\"maxPageCount\":500,\"prePrintStart\":500,\"desc\":\"\"},\"atmInfo\":{\"maxPageCount\":200,\"desc\":\"\"},\"dispatch\":{\"maxPageCount\":500,\"distributionStart\":500,\"distributionCharge_free\":300,\"distributionCharge\":200,\"desc\":\"\"},\"minCharge\":0,\"reduction\":{\"withCoupon\":false,\"newUser\":0,\"full\":[],\"redPacket\":false,\"activity\":[]}},{\"pointID\":97,\"status\":\"RUNNING\",\"pointType\":\"ATM\",\"delivery_scope\":\"Mary Rodriguez广场附近\",\"delivery_time\":\"每天中午十二点到两点，每天晚上8点到10点\",\"phone\":\"13151768843\",\"pointName\":\"华阳区大楼\",\"address\":\"软件园西北方向直走\",\"message\":\"新点开张！欢迎使用~\",\"image\":\"\",\"takeTime\":[14,53,24,0],\"price\":{\"A4\":{\"70g\":{\"mono\":{\"duplex\":38,\"oneside\":34}},\"80g\":{\"colorful\":{\"duplex\":23}}},\"A3\":{\"70g\":{\"mono\":{\"duplex\":43,\"oneside\":15}}}},\"printInfo\":{\"maxPageCount\":200,\"prePrintStart\":500,\"distributionStart\":1000,\"distributionCharge_free\":300,\"distributionCharge\":100},\"preInfo\":{\"maxPageCount\":500,\"prePrintStart\":500,\"desc\":\"\"},\"atmInfo\":{\"maxPageCount\":200,\"desc\":\"\"},\"dispatch\":{\"maxPageCount\":500,\"distributionStart\":500,\"distributionCharge_free\":300,\"distributionCharge\":200,\"desc\":\"\"},\"minCharge\":0,\"reduction\":{\"withCoupon\":false,\"newUser\":0,\"full\":[],\"redPacket\":false,\"activity\":[]}}]}\n";
        return Message.createMessage(Message.success_state,content);

    }

}
