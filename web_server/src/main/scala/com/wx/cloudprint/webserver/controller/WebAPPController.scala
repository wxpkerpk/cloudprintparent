package com.wx.cloudprint.webserver.controller

import java.io.Serializable

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation._
import org.json4s.JsonAST.{JInt, JString, JValue}
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import spray.json._
import DefaultJsonProtocol._
import com.wx.cloudprint.dataservice.service.{AddressService, PointService, ResService}
import com.wx.cloudprint.message.Message
import org.springframework.beans.factory.annotation.Autowired
import com.wx.cloudprint.util.{FileUtils, JsonUtil, MD5Util}
import java.util
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import org.springframework.beans.factory.annotation.Value
import javax.annotation.Resource
import scala.collection.JavaConverters._
import com.google.gson.Gson
import com.wx.cloudprint.dataservice.entity.{Point, Res}
import com.wx.cloudprint.server.covert.motan.GetIp //
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
  //    @MotanReferer(basicReferer = "basicRefererConfig")
  private var getIp: GetIp= _

  @Value("${file.path}")
  var filePath:String = _


  @Value("${server.port}")
  var port:String = _
  @Resource
  private var resService:ResService = _


  var serverIp: String = _
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

    if(point.size()>0) {
     val newPointMap= point.asScala.map(point2Map).asJava
      Message.createMessage(Message.success_state, newPointMap)
    }
    else
     Message.createMessage("EMPTY",point)
  }




  def main(s: Array[String]): Unit = {
    val data = FileUtils.readByte("C:\\Users\\wx\\Documents\\工作簿1.xlsx")
    val md5 = MD5Util.encrpt(data)
    System.out.println(md5)
  }

  @RequestMapping(value = Array("test"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody def test(request: HttpServletRequest): String = request.getSession.getId

  @RequestMapping(value = Array("file/url"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody def url: Message = {

    val urls=Map("url"->getIp()).asJava
    val message = Message.createMessage(Message.success_state, urls)
    message
  }

  private def genRestfulUrl(ip: String, port: String, prefix: String) = {
    val builder = new StringBuilder(80)
    builder.append("http://").append(ip).append(":").append(port).append(prefix)
    builder.toString
  }

  private[cotroller] def getFilePrefix(name: String) = {
    val temp = name.split("\\.")
    if (temp.length >= 2) temp(temp.length - 1)
    else null
  }


  @RequestMapping(value = Array("file/pic/preview"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody def Preview(request: HttpServletRequest, response: HttpServletResponse, @RequestParam md5: String, @RequestParam size: String, @RequestParam row: Int, @RequestParam col: Int, @RequestParam(required = false) page: Integer, @RequestParam(required = false) isMono: Boolean): Message = {
    val res = resService.getByMD5(md5)
    if (res != null) {
      val url = genRestfulUrl(res.getHost, res.getPort, "/API/file/pic/get/preview?" + String.format("md5=%s&size=%s&row=%s&col=%s&page=%s", md5, size, row, col, page))
      val map = new util.LinkedHashMap[String, String]
      map.put("img", url)
      return Message.createMessage(Message.success_state, map)
    }
    null
  }


  @RequestMapping(value = Array("file/page"), method = Array(RequestMethod.GET, RequestMethod.POST))
  def getPage(@RequestParam md5: String) = {
    val res = resService.getByMD5(md5)
    val message = new util.LinkedHashMap[String, AnyRef]
    if (res != null) {
      message.put("result", "EXISTED")
      message.put("pageCount", res.getPage.toString)
      message.put("direction", res.getDirection)
    }
    else message.put("result", "NO_EXIST")
    message
  }


}

case class money(oneside:Float,duplex :Float)
case class color(mono:money,colorful:money)
case class price(size:String,caliper:String,color:color)
object WebAPPController extends App{


  def price2json(price:price)={
    implicit def autoAsJsonNode(value: JValue) = asJsonNode(value)

   val str=  ("caliper" -> price.caliper) ~ ("size" -> price.size) ~ ("money" -> (
     "mono" -> ("oneside" -> price.color.mono.oneside) ~ ("duplex" -> price.color.mono.duplex)) ~ (
     "colorful" -> ("oneside" -> price.color.mono.oneside) ~ ("duplex" -> price.color.mono.duplex))
     )

    asJsonNode(str)
  }

 println( price2json( price("A4","70g",  color(money(1.0f,2.0f),money(1.0f,2.0f))) ))
  println( price("A4","70g",  color(money(1.0f,2.0f),money(1.0f,2.0f))) )


}

