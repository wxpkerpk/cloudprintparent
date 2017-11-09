package com.wx.cloudprint.controller

import com.wx.cloudprint.dataservice.entity.Order
import com.wx.cloudprint.dataservice.service.OrderService
import com.wx.cloudprint.dataservice.utils.JqGridPageView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._

@Controller
@RequestMapping(value = Array("/console/order"))
class OrdersController {


  @Autowired
  var orderservice:OrderService =_

  @RequestMapping(value = Array("/index"), method = Array(RequestMethod.GET))
  def index(): String = {
    "console/order/index"
  }

  @RequestMapping(value = Array("/search"), method = Array(RequestMethod.GET))
  @ResponseBody
  def search()={

    val list=orderservice.search(null,null,null,null,null,"id","desc",1,10)
    val jqGridPageView=new JqGridPageView[Order]()
    jqGridPageView.setMaxResults(list.getTotalCount)
    jqGridPageView.setRows(list.getResultList)
    jqGridPageView

  }


}