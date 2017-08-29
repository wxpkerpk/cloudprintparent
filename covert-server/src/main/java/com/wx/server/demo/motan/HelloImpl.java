package com.wx.server.demo.motan;

import com.weibo.api.motan.config.springsupport.annotation.MotanService;

@MotanService
public class HelloImpl implements Hello {
    @Override
    public String say(String content) {
        return content+
                "2";
    }
}
