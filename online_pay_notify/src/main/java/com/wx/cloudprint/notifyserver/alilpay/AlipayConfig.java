package com.wx.cloudprint.notifyserver.alilpay;

public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2018031302362551";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key ="MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQDGndS" +
            "QUdwKI1X690sRVj6+hQ/NNeCkDdjVznz8rqQHAH0FMLLqfwgbmtJM/apyxF+g9aHt1WGRvVWNH1SSbr13d11ENsGWbndXSx" +
            "cROLrW/j3PbyAb/5qANmb1uV1p2FqBJCJSJ8uS5OAyULH/qNiZjYdhBZcKwXOyPtwvbV/bRgVdQqE16UI0sRrVkBJVLeNWIyk/hOnL2" +
            "GtpzP5DUW7OQE9n5q7yF9HvVAUdPNxynh6NWC7uTnO1kICh+n+SfU2D9EquMSJ20NAa9W2Ff5XZrJLmmktmVx1KYQGFTdD9wWs1vBd41e6ado" +
            "UmIToSXbPlfRhfRXGtGeYhy8jTrnlZAgMBAAECggEAZER3c+gvelq+ZDUFi5oLkOjhwkpJvuhSqBVXGcUcf4BqnP0pxaIduLnfIwkITGoAkOSQO" +
            "932JLn9f4HqkwYe1jnHNtGhx0y7KLF60rHd+P1evOKvxhKyr7lHHxPWKcIUZo+UV/9JET5ERIJiaduWhWZiisNkrgRdYQXE6JiBxNLf0j4YEE" +
            "YZrX7gX3feyJJ2lqYFLR+rUDLzl3ITSyEvg10SpM07q540ZVeN33yFFUeSBMYmJFwWpB5BMER8M3XnWp/6FMyxCXglZDJ5dwLe/ZKlZUz" +
            "I30sRGqiyB2loli2EecCui6Lh9KwOQvqZ83zHb4vR8IxEZ2cxbQUwIF3Y5QKBgQDnMzKPaJU0Mvw/np5A+t4rA2N+Ke3QdIIY9" +
            "l0qvi4Vv8WwyB+R//u6R9uGkKYbghXlkUTO+hjSHCfr91QVXq79aD6HoENoDno+LDiiuweyEarfIO/w+zDr6Mdq8nkjpin+U" +
            "DVLXVm+zrg98I3yhKQ9ojwYyimSYoq2sSk1+tEApwKBgQDb6+I6t0Rc9vhWW0lUH5C3CbVXA4IwQNt3IcysGljdPAig6lqq" +
            "k6ZHZiJnVAQTD44V3lqlnK3xoseUx1QLQwpfu/Q+5avhznFV7KoxhxAmsWhlgZqNNTXlsZfhbTznORLMDVPph/qjcjf398" +
            "CpTXckqLM/qayHI4ij/ZBTvgn1/wKBgGLDgCm652HxFcinnbrsb9O4EtxmVOzjmF/Jfmsv6sfnsC+gjbv0ujgNUj3dtNe" +
            "zbl8u+1HfjJLU7Ejk0sp+NAqUaYHOeH0PEjFU+qypcNQkr/W5QEaVR4S/X9DT2+Paq72zzdBwBFJ6REJRbJHZD8A0ea+t" +
            "TKz344G/lHc2uempAoGAXXPSMZM5KKwGAWRPN8EmFLG6blxeUTTuTaR+PdAAXRqqq/6KQpv1d7et/OyVwqfEgAR5zpBS1j" +
            "CO0OkXDKYTXXUYKDYeoRjBznqAJAW51kdjDmdRjpnC6nVaDgAGdlcpZq5vLviKiDP8FpIgO5FghKJvcSkf2FlAMjeIwh0q" +
            "TG8CgYBENVk0/lclQqHjGii/xrqB7MGVJoMa9BIJkASTIUAtFU9wctiEF1TZ0IGE1XutvxziRmsmlSgyzieKjWYvBUJPh" +
            "w6REBNoLukidGJjpXra6x884MSpqvOX6IZoCoyaeV5dOPS+V9LiUJrvveDZA37E8fAXjOl05fAFpVFNPJTWyA==";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxp3UkFHcCiNV+vdLE" +
            "VY+voUPzTXgpA3Y1c58/K6kBwB9BTCy6n8IG5rSTP2qcsRfoPWh7dVhkb1VjR9Ukm69d3ddRDbBlm53V0sXETi61v49z28g" +
            "G/+agDZm9bldadhagSQiUifLkuTgMlCx/6jYmY2HYQWXCsFzsj7cL21f20YFXUKhNelCNLEa1ZASVS3jViMpP4Tpy9hracz" +
            "+Q1FuzkBPZ+au8hfR71QFHTzccp4ejVgu7k5ztZCAofp/kn1Ng/RKrjEidtDQGvVthX+V2ayS5ppLZlcdSmEBhU3Q/cFrNb" +
            "wXeNXumnaFJiE6El2z5X0YX0VxrRnmIcvI0655WQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://120.77.172.138:8080/API/alipayNotify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://127.0.0.1/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";
    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

}
