package com.wx.client.demo.motan.cotroller;

import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;
import com.wx.server.demo.motan.Hello;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SayHelloController {

    @MotanReferer(basicReferer = "basicRefererConfig")
    private Hello hello;

    @RequestMapping(value = "/")
    @ResponseBody
    public String index() {

        return hello.say("hello");
    }
}
