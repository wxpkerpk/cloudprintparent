package com.wx.cloudprint.controller

import java.text.SimpleDateFormat

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
  def search(state: String, start: String, end: String, pageNumber: Int, pageSize: Int, tel: String) = {

    val dataFormat = new SimpleDateFormat("yyyy-MM-dd")

    val startDate = if (start.equals("")) 0l else dataFormat.parse(start).getTime
    val endDate = if (start.equals("")) 0l else dataFormat.parse(end).getTime
    val telStr = if (tel.equals("")) null else tel


    val stateStr = if (state.equals("")) null else state
    //    orderservice.search(tel,null,)
    val list = orderservice.search(telStr, null, startDate, endDate, stateStr, "id", "desc", pageNumber, pageSize)
    val jqGridPageView=new JqGridPageView[Order]()
    //    jqGridPageView.setMaxResults(list.getTotalCount)
    jqGridPageView.setRows(list.getResultList)
    jqGridPageView.setPage(math.ceil(list.getTotalCount.toDouble / pageSize).toInt)
    jqGridPageView.setTotal(list.getTotalCount)
    jqGridPageView

  }

  @RequestMapping(value = Array("/edit"), method = Array(RequestMethod.GET))
  @ResponseBody
  def edit(orderId: String, state: String) = {
    orderservice.get("1")

    val order = orderservice.get(orderId)
    order.setPayState(state)
    orderservice.update(order)
    "success"


  }

}