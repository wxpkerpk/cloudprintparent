package com.wx.cloudprint.alipay;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradePrecreateModel;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.alipay.api.request.AlipayTradePrecreateRequest;

public class Alipay {
    public String open(String trade_no, String amount, String name, String descr) {
//实//获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(AlipayConfig.gatewayUrl, AlipayConfig.app_id, AlipayConfig.merchant_private_key, "json", AlipayConfig.charset, AlipayConfig.alipay_public_key, AlipayConfig.sign_type);

        //设置请求参数
        AlipayTradePrecreateRequest alipayRequest = new AlipayTradePrecreateRequest ();
        alipayRequest.setReturnUrl(AlipayConfig.return_url);
        alipayRequest.setNotifyUrl(AlipayConfig.notify_url);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = trade_no;
        //付款金额，必填
        String total_amount = amount;
        //订单名称，必填
        String subject = name;
        //商品描述，可空
        String body = descr;
        AlipayTradePrecreateModel model = new AlipayTradePrecreateModel();
        alipayRequest.setBizModel(model);
        model.setOutTradeNo(trade_no);
        model.setTotalAmount(amount);
        model.setSubject(name);

        //请求
        String result = null;
        try {
            result = alipayClient.execute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static void main(String []s){

        String re=new Alipay().open("12342234","1","test","234");
        System.out.println(re);


    }

}
