package com.wx.cloudprint.webserver.controller

import java.util

import com.google.gson.Gson
import com.wx.cloudprint.dataservice.entity.Point
import com.wx.cloudprint.dataservice.service.{AddressService, PointService}
import com.wx.cloudprint.message.Message
import com.wx.cloudprint.util.JsonUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody, RestController}

import scala.collection.JavaConverters._

@RestController
@RequestMapping(value = Array("/API"))
class PointAPIController {
  val gson = new Gson()
  @Autowired
  var pointService: PointService = _
  @Autowired
  var addressService: AddressService = _

  @RequestMapping(value = Array("point/points"), method = Array(RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS))
  @ResponseBody
  def getPoints(ID: String): Message = {
    def point2Map(point: Point) = {
      val pointJson = JsonUtil.toJson(point)
      val map = gson.fromJson(pointJson, classOf[util.Map[String, Object]])
      val priceJson = Option(map.get("price")).getOrElse("[]")
      val price = gson.fromJson(priceJson.toString, classOf[util.List[Object]])
      map.put("price", price)
      map

    }

    val point = pointService.getByAddressId(ID)
    val newPointMap = point.asScala.map(point2Map).asJava
    Message.createMessage(Message.success_state, newPointMap)
  }


  @RequestMapping(value = Array("point/address"), method = Array(RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS))
  @ResponseBody
  def getAddress(): Message = {
    val address = addressService.getRoot.asScala.map(_.toMap)
    Message.createMessage(Message.success_state, address.asJava)
  }

}
