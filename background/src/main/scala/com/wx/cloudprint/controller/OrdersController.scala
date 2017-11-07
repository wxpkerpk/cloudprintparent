package com.wx.cloudprint.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation._

@Controller
@RequestMapping(value = Array("/console/order"))
class OrdersController {


  @RequestMapping(value = Array("/index"), method = Array(RequestMethod.GET))
  def index(): String = {
    "console/order/index"
  }


}