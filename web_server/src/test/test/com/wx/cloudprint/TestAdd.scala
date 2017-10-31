package com.wx.cloudprint

import com.wx.cloudprint.dataservice.service.{AddressService, OrderService, PointService}
import com.wx.cloudprint.webserver.controller.OrderAPIController
import com.wx.cloudprint.webserver.register.StartWebApplication
import com.wx.cloudprint.webserver.service.SignService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
@RunWith(classOf[SpringRunner])
@SpringBootTest(classes = Array(classOf[StartWebApplication]))
class TestAdd  {
  @Autowired
  var pointService:PointService=_
  @Autowired
  var addressService :AddressService=_


  @Autowired
  var orderService:OrderService=_
  @Autowired
  var gisnservice:SignService=_

  @Autowired
  var orderController:OrderAPIController=_


  @Test
  def add():Unit={
    //     val ss=gisnservice.getCode("18011302985")
    //    val map=gisnservice.signin("18011302985","666666pk")

    val is = gisnservice.isRegister("18011302985")
    print(is)

//    val order = "{\n  \"id\": \"7276d189-1ea1-43c5-9d95-e2503f1f72f5\",\n  \"files\": [{\n    \"fileID\": \"A52B4686E173B0612B71148F7F9E099A\",\n    \"fileName\": \"申报指南.docx\",\n    \"layout\": 2,\n    \"copies\": 1,\n    \"size\": \"A4\",\n    \"caliper\": \"70g\",\n    \"color\": \"mono\",\n    \"side\": 1,\n    \"startPage\": 0,\n    \"endPage\": 0\n  }],\n  \"money\": 501.0,\n  \"couponID\": 0,\n  \"reduction\": {\n    \"newUser\": false,\n    \"full\": []\n  },\n  \"dispatching\": {\n    \"username\": \"\",\n    \"userPhone\": \"\",\n    \"address\": \"\",\n    \"leftMessage\": \"\"\n  }\n}"
//
//    orderController.verify(order)

//   val order=orderController.detail("150756263242510000")
//    println(compact(render(order)))




  }
}
