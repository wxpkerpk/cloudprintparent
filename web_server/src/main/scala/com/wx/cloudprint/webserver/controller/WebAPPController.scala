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
import util.JsonUtil
import java.util

import com.google.gson.Gson
import com.wx.cloudprint.dataservice.entity.Point //
@RestController
@RequestMapping(value = Array("/API"))
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
    Message.createMessage(Message.success_state, Array(address))
  }
  val gson=new Gson()

  @RequestMapping(value = Array("point/points"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody
  def getPoints(ID:String): Message = {
    def point2Map(point:Point)={
      val pointJson=JsonUtil.toJson(point)
      val map= gson.fromJson(pointJson,classOf[util.Map[String,Object]])
      val priceJson= map.get("price")
      val price=gson.fromJson(priceJson.toString, classOf[util.List[Object]])
      map.put("price",price)
      map

    }
    val point=pointService.getByAddressId(ID)
    import scala.collection.JavaConverters._

    if(point.size()>0) {
     val newPointMap= point.asScala.map(point2Map).asJava
      Message.createMessage(Message.success_state, newPointMap)
    }
    else
     Message.createMessage("EMPTY",point)
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

