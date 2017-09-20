package com.wx.cloudprint.webserver.controller

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody, RestController}
import org.json4s.JsonAST.{JInt, JString, JValue}
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import spray.json._
import DefaultJsonProtocol._
import com.wx.cloudprint.dataservice.service.{AddressService, PointService}
import message.Message
import org.springframework.beans.factory.annotation.Autowired
import util.JsonUtil //
@RestController
@RequestMapping(value = Array("/test"))
class WebAPPController {
  implicit def autoAsJsonNode(value: JValue) = asJsonNode(value)

  @Autowired
  var pointService:PointService=_
  @Autowired
  var addressService :AddressService=_

  @RequestMapping(value = Array("point/address"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody
  def getAddress(): Message = {
    val address=addressService.getRoot.toMap
    Message.createMessage(Message.success_state, address)
  }

  @RequestMapping(value = Array("point/points"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody
  def getPoints(ID:String): Message = {
    val point=pointService.getByAddressId(ID)

    Message.createMessage(Message.success_state, point)
  }

}

case class money(oneside:Float,duplex :Float)
case class color(mono:money,colorful:money)
case class price(size:String,caliper:String,color:color)
object WebAPPController extends App{


  def price2json(price:price)={
    implicit def autoAsJsonNode(value: JValue) = asJsonNode(value)

   val str=  (("caliper"->price.caliper)~("size"->price.size)~("money"->(
    "mono"->("oneside"->price.color.mono.oneside)~("duplex"->price.color.mono.duplex))~(
      "colorful"->("oneside"->price.color.mono.oneside)~("duplex"->price.color.mono.duplex))
      ))

    asJsonNode(str)
  }

 println( price2json( price("A4","70g",  color(money(1.0f,2.0f),money(1.0f,2.0f))) ))
  println( price("A4","70g",  color(money(1.0f,2.0f),money(1.0f,2.0f))) )


}

