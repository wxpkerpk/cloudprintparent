package com.wx.cloudprint.webserver.controller


import com.fasterxml.jackson.databind.JsonNode
import org.json4s._
import org.json4s.jackson.JsonMethods._
import com.google.gson.Gson
import com.wx.cloudprint.dataservice.entity.{Dispatch, Order, Point}
import com.wx.cloudprint.dataservice.service.{AddressService, PointService}
import com.wx.cloudprint.message.Message
import com.wx.cloudprint.webserver.anotation.Acess
import org.json4s.JsonAST.JValue
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody, RestController}
import org.json4s.JsonDSL._
@RestController
@RequestMapping(value = Array("/API"))
class OrderAPIController extends BaseController {
  implicit val formats = DefaultFormats


  val gson = new Gson()
  @Autowired
  var pointService: PointService = _
  @Autowired
  var addressService: AddressService = _

  import org.springframework.beans.factory.annotation.Autowired
  import javax.servlet.http.HttpServletRequest
  implicit def autoAsJsonNode(value: JValue) = asJsonNode(value)


  @RequestMapping(value = Array("/order/verify"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody
  @Acess(authorities = Array("user"))
  def verify(param: String) : JsonNode= {
    val user = getUser()
    import OrderAPIController.caculatePrice
    val session = request.getSession
    val json = parse(param)
    val pointId = json \ "id"
    val point = pointService.get(pointId.extract[String])
    val dispatch = (json \ "dispatching").toOption.orNull
    val pointdispatch = if (dispatch == null) null else point.getDispatch

    val money=(json \ "money").extract[Float]
    val sum = caculatePrice(param, point.getPrice, pointdispatch)
    if(sum!=money){
       ("result" -> Message.fail_state) ~ ("info" -> "后台金额核对不一致")
    }else{
      val order=new Order
      order.setId(BaseController.makeTimeId)
      order.setFiles(compact(render(json \ "files")))
      order.setMoney(money)
      order.setUser(user)
      order.setPointId(pointId.extract[String])
      if(dispatch!=null) order.setDispatching(compact(render(dispatch)))

      ("result" -> Message.success_state) ~ ("info" -> (("orderID"->"")~("desc"->"")))

    }


  }
}

object OrderAPIController extends App {

  implicit val formats = DefaultFormats

  def caculatePrice(order: String, price: String, dispatch: Dispatch) = {
    implicit val formats = DefaultFormats
    val json = parse(order)
    val files = json \ "files"
    val priceMap = parse(price).extract[List[Map[String, Any]]]
    var sum = 0f
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
      sum += page * perPrice.toString.toFloat * copies
    }
    sum + (if (dispatch == null) 0f else dispatch.getDistributionCharge)


  }


  val str = "[{\"caliper\":\"70g\",\"size\":\"A4\",\"money\":{\"mono\":{\"oneside\":1.0,\"duplex\":2.0},\"colorful\":{\"oneside\":1.0,\"duplex\":2.0}}}]"
  val order = "{\n  \"id\": \"0026\",\n  \"files\": [{\n    \"fileID\": \"A52B4686E173B0612B71148F7F9E099A\",\n    \"fileName\": \"申报指南.docx\",\n    \"layout\": 2,\n    \"copies\": 1,\n    \"size\": \"A4\",\n    \"caliper\": \"70g\",\n    \"color\": \"mono\",\n    \"side\": 1,\n    \"startPage\": 0,\n    \"endPage\": 0\n  }],\n  \"money\": 25,\n  \"couponID\": 0,\n  \"reduction\": {\n    \"newUser\": false,\n    \"full\": []\n  },\n  \"dispatching\": {\n    \"username\": \"\",\n    \"userPhone\": \"\",\n    \"address\": \"\",\n    \"leftMessage\": \"\"\n  }\n}"

  val test = "{\n  \"pointID\": \"0026\",\n  \"money\": 25,\n  \"files\": [{\n    \"fileID\": \"A52B4686E173B0612B71148F7F9E099A\",\n    \"fileName\": \"申报指南.docx\",\n    \"row\": 1, \n    \"col\": 2,\n    \"copies\": 1,\n    \"size\": \"A4\",\n    \"caliper\": \"70g\",\n    \"color\": \"mono\",\n    \"side\": 1,\n    \"startPage\": 0,\n    \"endPage\": 0\n  }],\n  \"dispatching\": {\n    \"nickname\": \"\",\n    \"phone\": \"\",\n    \"address\": \"\",\n    \"message\": \"\"\n  }\n}"
  val json = parse(test)


  println(compact(render(json \ "files")))

}
