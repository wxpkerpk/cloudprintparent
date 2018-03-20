package com.wx.cloudprint.alipay;

public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2018031302362551";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQDKAhV9voeNln2XpHjb5jks0+wdvJ8tU/UHDNylZfxp0HAw5QKMxJyPloiRFnNtCbmVR5/gnDbIl+cn1yXw6ZJ5WNFCCxnr/jTd8bDcJMbDb7C7j91A1ezs+Ak8P1g2YZQtvxS5SA3mPlIlkQBWLj+ZzPH8ip82a7Bho1Z+NPdbzaGIrrPpd6nuzsy5bt1/MYDoOLnW7vXFAeJbZ0QOTCh6xy2ukbGRMYJZ0TTHbeIhorOEJghGMzL4mzspTuvsEuvl8ta45DVxBrx6vRjs/38lpg9+2sVSXC8mLhpO/12HLWcP88p43eLcMEs7sepHnkXLrywK9HMYYbVJ3mGSAgN/AgMBAAECggEAPu0dQrlaQnnRj65VU7LItrUhRTYfo6AWoOaZyRAW6+NZP3HnNHB+kKAE1KVYuWuhGuZf4DjpO91NzXRQxlYMIa7QKLGKlp/uWgDS5hCyteiR9itfcbXyAvNU9SaKVQ5aeVGfpAPVCJb7QeaEY1Quzbsw2klYRTyQiim2ZMQdFcRPeMJlipzQxrjF0gm2hdnAESRiQBbUU9QA5JNeULaveBBBI+rYqyn2pK2L9/2m/b3+UoWCbh1G+zzWcugUcPgf2Cwp7z1pZ3+hxK4h9a/v8o447sFr2Xf7TF0cdR+8QpuYaYnEpI2BiyNXXOvFZaqQfg6WhCra6eAkeqY5i7pQCQKBgQDz/vvBPrygB6GmFruq/feO2cRBIujxiiqrBDLt2SzOFH6lTLM6Xf8cN16ZXLYO02lsipQEV4B3olGeYguWLjtbeeSaFvOqdJmnEUBlTgdZYPs7oC3qUrtAaBFKA/A3xuVK+rqAMVgfGJEk7hzg9fBRgPQvVX1HjU328O9vF4Oe7QKBgQDT8khX41fk52UsBHqJEo8njXcGUmAFoEVeiGURWbauXoMahNrvQYJnwbaaMZqiC1YuQKKxo3AeggQe62vSUuh4WsAmcdHwDtWGN1d5uhtThRA0rWbjagS29hXH84dpb8cppjgfJqdh+IDignX4qSUkoAM4w9gQaRfz1Q5YKhCymwKBgQCn4AERRXv8UyPaJXv7uv8wDw6jjkRdo6WVwq7TzP+xzpIQY0RRXUjf/gydUheE/dpkLriGgDnIliq34Vd9fdOjLaumhTIjTlRFiW4YBWazDTHuJVhSB5gESVXT6f/GMJekTfwxpUGvK3T5xsah1TUCrsnTI2ut5kAFLmJeufv6RQKBgQCjsfgP8WX8RH/OfHMLlUr9mHQ6bLr+/Wc2eQ/eU7UVfaeyfrP9oFIQwvu+ods1lp3sQT2nB31cAr0eCGQRYLlt42GPD8++oiZmcC3etU/PtPDsTpUtxb94dB4kfcGzPILNdzTXUmpq75zT1UE6WaF3ZSK34ALrwc+fIonerDaTSwKBgQCvRpOKzQp5rkbSl9uiqR2zYcyiM5o+fN2CUL69NFVKbe3+Thf1e6Md/iBKPhA8HQSM39EAfv/p04Y2unKjwPugOmL0TMEQQuZgbtnfyZbMkmAz7VppC9d9nAjfk6zdIv15niY+EBuLkXyUNXRYqiVgjHww+Q8xVw7YlBth94mktg==";
    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAygIVfb6HjZZ9l6R42+Y5LNPsHbyfLVP1BwzcpWX8adBwMOUCjMScj5aIkRZzbQm5lUef4Jw2yJfnJ9cl8OmSeVjRQgsZ6/403fGw3CTGw2+wu4/dQNXs7PgJPD9YNmGULb8UuUgN5j5SJZEAVi4/mczx/IqfNmuwYaNWfjT3W82hiK6z6Xep7s7MuW7dfzGA6Di51u71xQHiW2dEDkwoesctrpGxkTGCWdE0x23iIaKzhCYIRjMy+Js7KU7r7BLr5fLWuOQ1cQa8er0Y7P9/JaYPftrFUlwvJi4aTv9dhy1nD/PKeN3i3DBLO7HqR55Fy68sCvRzGGG1Sd5hkgIDfwIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://120.77.172.138:8099/API/alipayNotify";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://127.0.0.1/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";
    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";

}
