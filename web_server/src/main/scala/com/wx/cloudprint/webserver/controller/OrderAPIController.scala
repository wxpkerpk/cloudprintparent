package com.wx.cloudprint.webserver.controller


import com.fasterxml.jackson.databind.JsonNode
import org.json4s._
import org.json4s.jackson.JsonMethods._
import com.google.gson.Gson
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

@RestController
@RequestMapping(value = Array("/API"))
class OrderAPIController extends BaseController {
  implicit val formats = DefaultFormats


  val gson = new Gson()
  @Autowired
  var pointService: PointService = _
  @Autowired
  var addressService: AddressService = _
  @Autowired
  var orderService: OrderService = _

  implicit def autoAsJsonNode(value: JValue) = asJsonNode(value)

  @RequestMapping(value = Array("/orders/info"), method = Array(RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS))
  @ResponseBody
  def getInfo(page: Int, limits: Int, @RequestParam(name = "type") state: String):JsonNode = {

    val orders = orderService.getOrders(page, limits, state)
    val orderstr= orders.asScala.map { x =>
      val files = parse(x.getFiles).extract[Array[Map[String, Any]]]
      val name = files(0).getOrElse("fileName", "")
      val count = files.length
      render(("orderID" -> x.getId) ~ ("orserDate" -> x.getOrderDate) ~ ("state" -> x.getPayState) ~ ("money" -> x.getMoney) ~ ("pointName" -> x.getPointName) ~ ("fileCount" -> count) ~ ("filePrename" -> name.toString))
    }.mkString("[",",","]")

    parse(orderstr)

  }

  @RequestMapping(value = Array("/orders/detail"), method = Array(RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS))
  @ResponseBody
  def detail(orderID: String): JsonNode = {
    val order = orderService.get(orderID)

    val files = parse(order.getFiles)
    val settles = parse(order.getSettle)
    val dispatch = parse(order.getDispatching)

    val id = order.getId
    val userName = order.getUser.getTel
    val nickName = order.getUser.getNickName
    val orderDate = order.getOrderDate
    val state = order.getPayState
    val payway = order.getPayWay
    val pointName = order.getPointName
    val pointId = order.getPointId
    val printDate = order.getPrintDate
    val money = order.getMoney
    val point = pointService.get(pointId)


    ("result" -> "OK") ~ ("info" -> (("uid" -> order.getUser.getId) ~ ("username" -> userName) ~ ("nickname" -> nickName) ~ ("orderID" -> id) ~ ("orderDate" ->
      orderDate) ~ ("state" -> state) ~ ("payway" -> payway) ~ ("pointName" -> pointName) ~ ("pointPhone" -> (if (point == null) "" else point.getPhone))
      ~ ("printDate" -> printDate) ~ ("printID" -> pointId) ~ ("money" -> money) ~ ("files" -> files) ~ ("dispatching" -> dispatch) ~ ("settle" -> settles)
      ))


  }

  @RequestMapping(value = Array("/order/verify"), method = Array(RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS))
  @ResponseBody
  @Acess(authorities = Array("user"))
  def verify(order: java.util.HashMap[String,Object]): JsonNode = {

    val user = getUser()
    import OrderAPIController.caculatePrice
    import OrderAPIController.getSettles
    val param=gson.toJson(order)
    val json = parse(param)
    val pointId = json \ "id"
    val point = pointService.get(pointId.extract[String])
    val dispatch = (json \ "dispatching").toOption.orNull
    val pointdispatch = if (dispatch == null) null else point.getDispatch

    val money = (json \ "money").extract[Float]
    val settles = getSettles(param, point.getPrice)
    val sum = caculatePrice(settles, pointdispatch)
    if (sum != money) {
      ("result" -> Message.fail_state) ~ ("info" -> "后台金额核对不一致")
    } else {
      val order = new Order
      order.setId(BaseController.makeTimeId)
      order.setFiles(compact(render(json \ "files")))
      order.setMoney(money)
      order.setOrderDate(System.currentTimeMillis())
      order.setUser(user)
      val settlesStr = settles.map { x => compact(render(x)) }.mkString("[", ",", "]")
      order.setSettle(settlesStr)
      order.setPointId(pointId.extract[String])
      if (dispatch != null) order.setDispatching(compact(render(dispatch)))
      orderService.add(order)
      ("result" -> Message.success_state) ~ ("info" -> (("orderID" -> "") ~ ("desc" -> "")))

    }


  }
}

object OrderAPIController extends App {


  def getSettles(order: String, price: String) = {
    implicit val formats = DefaultFormats

    val json = parse(order)
    val files = json \ "files"
    val priceJson = parse(price)
    val priceMap = priceJson.extract[Array[Map[String, Any]]]
    val settles = new ArrayBuffer[JValue]()
    for (JObject(child) <- files) {
      val values = child.toMap
      val copies = values("copies").extract[Int]
      val caliper = values("caliper").extract[String]
      val color = values("color").extract[String]
      val side = values("side").extract[Int]
      val size = values("size").extract[String]
      var page = values("endPage").extract[Int] - values("startPage").extract[Int] + 1
      val map = priceMap.filter(x => {
        x("caliper").equals(caliper) && x("size").equals(size)
      }).head("money").asInstanceOf[Map[String, Map[String, Any]]]
      val money = map(color)
      val perPrice = if (side == 1) money("oneside") else money("duplex")
      page = if (page > 0) page else 0
      val node = ("size" -> size) ~ ("caliper" -> caliper) ~ ("color" -> color) ~ ("side" -> side) ~ ("unit" -> perPrice.toString.toFloat) ~ ("count" -> page * copies)
      settles += node
    }
    settles.toArray
  }

  def caculatePrice(settles: Array[JValue], dispatch: Dispatch) = {
    implicit val formats = DefaultFormats

    var sum = settles.map { x => (x \ "unit").extract[Float] * (x \ "count").extract[Int] }.sum
    sum += (if (dispatch != null) dispatch.getDistributionStart else 0f)
    sum

  }


  val str = "[{\"caliper\":\"70g\",\"size\":\"A4\",\"money\":{\"mono\":{\"oneside\":1.0,\"duplex\":2.0},\"colorful\":{\"oneside\":1.0,\"duplex\":2.0}}}]"
  val order = "{\n  \"id\": \"7276d189-1ea1-43c5-9d95-e2503f1f72f5\",\n  \"files\": [{\n    \"fileID\": \"A52B4686E173B0612B71148F7F9E099A\",\n    \"fileName\": \"申报指南.docx\",\n    \"layout\": 2,\n    \"copies\": 1,\n    \"size\": \"A4\",\n    \"caliper\": \"70g\",\n    \"color\": \"mono\",\n    \"side\": 1,\n    \"startPage\": 0,\n    \"endPage\": 0\n  }],\n  \"money\": 25,\n  \"couponID\": 0,\n  \"reduction\": {\n    \"newUser\": false,\n    \"full\": []\n  },\n  \"dispatching\": {\n    \"username\": \"\",\n    \"userPhone\": \"\",\n    \"address\": \"\",\n    \"leftMessage\": \"\"\n  }\n}"

  val test = "{\n  \"pointID\": \"0026\",\n  \"money\": 25,\n  \"files\": [{\n    \"fileID\": \"A52B4686E173B0612B71148F7F9E099A\",\n    \"fileName\": \"申报指南.docx\",\n    \"row\": 1, \n    \"col\": 2,\n    \"copies\": 1,\n    \"size\": \"A4\",\n    \"caliper\": \"70g\",\n    \"color\": \"mono\",\n    \"side\": 1,\n    \"startPage\": 0,\n    \"endPage\": 0\n  }],\n  \"dispatching\": {\n    \"nickname\": \"\",\n    \"phone\": \"\",\n    \"address\": \"\",\n    \"message\": \"\"\n  }\n}"
  val json = parse(test)


  println(compact(render(json \ "files")))

}
