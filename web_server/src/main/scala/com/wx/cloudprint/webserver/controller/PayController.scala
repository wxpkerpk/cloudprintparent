package com.wx.cloudprint.webserver.controller
import com.fasterxml.jackson.databind.JsonNode
import com.wx.cloudprint.alipay.Alipay
import com.wx.cloudprint.dataservice.service.OrderService
import org.json4s.JsonAST.JValue
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._

@RequestMapping(value = Array("/API"))
@RestController
class PayController extends BaseController{


  implicit def autoAsJsonNode(value: JValue) = asJsonNode(value)

  @Autowired
  var orderService: OrderService = _

  @RequestMapping(value = Array("/pay/payment"), method = Array(RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS))
  @ResponseBody
  def payment(orderID: String, payway: String, isTest: String): JsonNode = {
    val order=orderService.get(orderID)
    if("ALIPAY".equals(payway)){

      var money = (order.getMoney / 100).toString
      if (Option(isTest).getOrElse("false").equals("true")) {
        money = 0.01.toString
      }
      val re = new Alipay().open(orderID, money, "知书云打印", "")
      //order.setPayState(Order.States.PAID.toString) //测试用
      order.setPayWay("ALIPAY")
      orderService.add(order)

      ("result" -> "OK") ~ ("info" -> ("payform" -> re)) ~ ("test" -> 1)
    }else{
      ("result" -> "ERROR") ~ ("message" -> "没有开放这个支付")
    }

  }
  @RequestMapping(value = Array("/pay/trade"), method = Array(RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS))
  @ResponseBody
  def trade(orderID:String):JsonNode={
    val order=orderService.get(orderID)

    ("result"->"OK")~("info"->order.getPayState )

  }
}
