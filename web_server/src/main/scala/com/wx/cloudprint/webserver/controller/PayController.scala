package com.wx.cloudprint.webserver.controller
import com.fasterxml.jackson.databind.JsonNode
import com.wx.cloudprint.alipay.Alipay
import com.wx.cloudprint.dataservice.entity.Order
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
  def payment(orderID:String,payway:String):JsonNode={
    val order=orderService.get(orderID)
    if("ALIPAY".equals(payway)){
      val re = new Alipay().open(orderID, order.getMoney.toString, "test", "")
      order.setPayState(Order.States.PAYING.toString)
      orderService.add(order)

      val s = 2
      ("result" -> "OK") ~ ("info" -> re) ~ ("test" -> 1)
    }else{
      ("result"->"ERROR")~("info"->"没有开放这个支付")
    }

  }
  @RequestMapping(value = Array("/pay/trade"), method = Array(RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS))
  @ResponseBody
  def trade(orderID:String):JsonNode={
    val order=orderService.get(orderID)

    ("result"->"OK")~("info"->order.getPayState )

  }
}