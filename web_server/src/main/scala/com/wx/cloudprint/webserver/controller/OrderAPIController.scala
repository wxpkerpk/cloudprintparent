package com.wx.cloudprint.webserver.controller


import org.json4s._
import org.json4s.jackson.JsonMethods._
import com.google.gson.Gson
import com.wx.cloudprint.dataservice.entity.{Dispatch, Point}
import com.wx.cloudprint.dataservice.service.{AddressService, PointService}

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody, RestController}


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
    import OrderAPIController.caculatePrice
    val session = request.getSession
    val json = parse(param)
    val pointId = json \ "id"
    val point = pointService.get(pointId.extract[String])

    val sum = caculatePrice(param, point.getPrice, point.getDispatch)



  }
}

object OrderAPIController extends App {



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
    sum + (if(dispatch==null) 0f
    else dispatch.getDistributionCharge)


  }


  val str = "[{\"caliper\":\"70g\",\"size\":\"A4\",\"money\":{\"mono\":{\"oneside\":1.0,\"duplex\":2.0},\"colorful\":{\"oneside\":1.0,\"duplex\":2.0}}}]"
  val order = "{\n  \"id\": \"0026\",\n  \"files\": [{\n    \"fileID\": \"A52B4686E173B0612B71148F7F9E099A\",\n    \"fileName\": \"申报指南.docx\",\n    \"layout\": 2,\n    \"copies\": 1,\n    \"size\": \"A4\",\n    \"caliper\": \"70g\",\n    \"color\": \"mono\",\n    \"side\": 1,\n    \"startPage\": 0,\n    \"endPage\": 0\n  }],\n  \"money\": 25,\n  \"couponID\": 0,\n  \"reduction\": {\n    \"newUser\": false,\n    \"full\": []\n  },\n  \"dispatching\": {\n    \"username\": \"\",\n    \"userPhone\": \"\",\n    \"address\": \"\",\n    \"leftMessage\": \"\"\n  }\n}"

 println(caculatePrice(order,str,null))

}
