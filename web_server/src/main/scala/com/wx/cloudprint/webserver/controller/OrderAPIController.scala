package com.wx.cloudprint.webserver.controller

import java.util
import java.util.stream.Collectors
import javax.servlet.http.{HttpServletRequest, HttpSession}
import org.json4s._
import org.json4s.jackson.JsonMethods._
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
class OrderAPIController {
  implicit val formats = DefaultFormats

  val gson = new Gson()
  @Autowired
  var pointService: PointService = _
  @Autowired
  var addressService: AddressService = _

  import org.springframework.beans.factory.annotation.Autowired
  import javax.servlet.http.HttpServletRequest

  @Autowired var request: HttpServletRequest = _

  @RequestMapping(value = Array("/order/verify"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody
  def verify(param: String) = {
    val session = request.getSession
    val json = parse(param)
    val pointId = json \ "id"
    val files = json \ "files"
    val point = pointService.get(pointId.extract[String])
    val priceMap = scala.util.parsing.json.JSON.parseFull(point.getPrice).get.asInstanceOf[List[Map[String, Any]]]
    var sum=0f
    val price =  for (JObject(child) <- files) {
      val values = child.toMap
      val copies = values("copies").extract[Int]
      val caliper = values("caliper").extract[String]
      val color = values("color").extract[String]
      val side = values("side").extract[Int]
      val size = values("size").extract[String]
      var page = values("endPage").extract[Int] - values("startPage").extract[Int]
      val map = priceMap.filter(x => {
        x("caliper") == caliper && x("size") == side
      }).head("money").asInstanceOf[Map[String,Map[String,Any]]]
     val perPrice= if(side==1)  map("oneside").toString().toFloat else map("duplex").toString().toFloat
      page=if(page>0) page else 0
      sum += page*perPrice
    }


}

object OrderAPIController extends App {

  implicit val formats = DefaultFormats

  val str = "[{\"caliper\":\"70g\",\"size\":\"A4\",\"money\":{\"mono\":{\"oneside\":1.0,\"duplex\":2.0},\"colorful\":{\"oneside\":1.0,\"duplex\":2.0}}}]"
  val priceMap = scala.util.parsing.json.JSON.parseFull(str).get.asInstanceOf[List[Map[String, Any]]]
  priceMap.filter(x => {
    x("caliper") == "" && x("size") == ""
  })
  println(priceMap)


}
