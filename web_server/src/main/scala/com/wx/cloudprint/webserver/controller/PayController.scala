package com.wx.cloudprint.webserver.controller
import com.fasterxml.jackson.databind.JsonNode
import org.json4s._
import org.json4s.jackson.JsonMethods._
import com.google.gson.Gson
import com.wx.cloudprint.alipay.Alipay
import com.wx.cloudprint.dataservice.entity.{Dispatch, Order, Point}
import com.wx.cloudprint.dataservice.service.{AddressService, OrderService, PointService}
import com.wx.cloudprint.message.Message
import com.wx.cloudprint.webserver.anotation.Acess
import org.json4s.JsonAST.JValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._
import org.json4s.JsonDSL._

import scala.collection.JavaConverters._
import scala.collection.mutable.ArrayBuffer
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
      ("result"->"OK")~("info"->re)
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
