package com.wx.cloudprint.notifyserver.alilpay;

public class AlipayConfig {
    // 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
    public static String app_id = "2016080700190999";

    // 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key ="MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQDA6MDdydoTyWW5\n" +
            "urWnRhImxFguwRwKhIe4eOFdKiKpoq2pBk6U12eHR7nZChFeBggdgOOEGEn736KJ\n" +
            "Ep/yLTwkdAP/ZvdupA2gXv3pS51tjOwTWrAxPTayGSUNlqK3w3tLNz9gDA2idjqB\n" +
            "CyrWrLdK3Gchps6f/6dCUsTXk0OV60fxZ9XfWkoD78/+jwy6WD4bzXJPEZNZzLeQ\n" +
            "fYpvZNZgR3RoWL0MTz13rebmn8rcLwupcW0BpFzozDaGQ6XPB4kim0wfaWGhtM4l\n" +
            "2FSAxoTB0NraCEdRdVrmdu/aEhzr22vATPbEeGj9fPh+qHsfaGuRH/CeNWJh+JoR\n" +
            "lL1JxplpAgMBAAECggEAbaqkIjHjWQZQX1LGfC64mPNcElBT6aLIKVYHAFi41ekW\n" +
            "gpWETNdcEqry+3DeyK3zJRqlvO21+Yt8MiK1bQ7MNwkBUMuvdGWtHXODM4J6H9JS\n" +
            "TvpqWcOr+XmT9/Kf1Io76FmjHK4dfjNqNPwtQVd06Aj+VEIpCwpigrDdGJSugmX+\n" +
            "t+SfWL4b3mVu5320TwfihAcU1SNdUt9lsSBiM/dYsXdwZLfvwmIUFWtfuL+JfW/t\n" +
            "cw3u4wsTHtiw4QW5SONXXu0/3LuXfiXBgvjfpzGveJch5JJbEQUJ5PxqQPUEyeZR\n" +
            "gubd5ZZDYGU2jvrZxr1bLje+J2rjOtk26IEsAVlcpQKBgQDxfoGODyt/Pdvb+LCT\n" +
            "/MTB0XwGBiBSv8uDn7TSlWOqtIjpauUL5JVt36WXWzuRNnvf5AglSld2vn+8Xj28\n" +
            "j+ESgfc1ou3aSIvAyQmcvK/R6hhpgAcd0RsRy3ZRjO5PybvrC0ScmomFZpdI9tx+\n" +
            "jIQbpqYJFItbUjXKZCS/qFvQqwKBgQDMfyYF3i0gA6fOMpGeZX/18EDUfCwyd0XS\n" +
            "VOkZjroVZVBNb9PYHNYKm2x22Mzyh9W2lwcv+ej02zu02+EX2YqoRuKcOi3lfEN5\n" +
            "afTQdefqOryxVpE2V09xg8k1Gc9neX1RtqwYFlliyM/3VUUnpvDqG8ACN61ELsD9\n" +
            "9rmcsCyGOwKBgQCGKlxbyj3GMV3GdgHUFehSLcoTpq+qiXfNpprVK731httRotuh\n" +
            "odsK8eeTkVIkuPpVRuDnpse05t0cj9cIvX6zw5TOLbirPwa1kntOot2jZ8UsR76s\n" +
            "aBH77ufLItr7ZP6L9PSndIvWm0qNdOjHPdXfbtRr0wghMilESiLIgAaShQKBgQCe\n" +
            "1kPaWvAB5dT5PB4PQhdaF/rDEI/jwvDgpkNILqwq4j61Zx7Mxdka9A4TYeIOn6NH\n" +
            "55cVlK91QLPnjsIPnFEu06BAaJAtp49hRwN4x5wTOvxFYk/4PWjfatVmAV4vqRXF\n" +
            "2nVhYDqdmAywt/K3Edweet9w2X4jb41HtdQF+HEc8QKBgE1hAPUELXMQvsDojLyL\n" +
            "shY7iqTcjqmhaVgyX/oPn5H/tUXtVKV7h6Qx5Jl6mcXX1boUlNrlj3PmcLe6ZIHB\n" +
            "MWaMf8ijz56grFxlQalzJGKW0/LpOXLOrc5c1TYvXA+khnMS+xaUJBqMeUfl9dsR\n" +
            "rzNddEZdcwoOjIvwt21n0JY5";

    // 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAwOjA3cnaE8llubq1p0YS\n" +
            "JsRYLsEcCoSHuHjhXSoiqaKtqQZOlNdnh0e52QoRXgYIHYDjhBhJ+9+iiRKf8i08\n" +
            "JHQD/2b3bqQNoF796UudbYzsE1qwMT02shklDZait8N7Szc/YAwNonY6gQsq1qy3\n" +
            "StxnIabOn/+nQlLE15NDletH8WfV31pKA+/P/o8Mulg+G81yTxGTWcy3kH2Kb2TW\n" +
            "YEd0aFi9DE89d63m5p/K3C8LqXFtAaRc6Mw2hkOlzweJIptMH2lhobTOJdhUgMaE\n" +
            "wdDa2ghHUXVa5nbv2hIc69trwEz2xHho/Xz4fqh7H2hrkR/wnjViYfiaEZS9ScaZ\n" +
            "aQIDAQAB";

    // 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String notify_url = "http://127.0.0.1/alipay.trade.page.pay-JAVA-UTF-8/notify_url.jsp";

    // 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
    public static String return_url = "http://127.0.0.1/alipay.trade.page.pay-JAVA-UTF-8/return_url.jsp";
    // 签名方式
    public static String sign_type = "RSA2";

    // 字符编码格式
    public static String charset = "utf-8";

    // 支付宝网关
    public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";

}
