package com.wx.cloudprint.controller

import com.wx.cloudprint.dataservice.entity.Point
import com.wx.cloudprint.dataservice.service.PointService
import com.wx.cloudprint.dataservice.utils.JqGridPageView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod}

@Controller
@RequestMapping(value = Array("/console/point"))
class PointController {
  @Autowired
  var pointService: PointService = _

  @RequestMapping(value = Array("/index"), method = Array(RequestMethod.GET))
  def index(): String = {
    "console/point/index"
  }

  @RequestMapping(value = Array("/search"), method = Array(RequestMethod.GET))
  def search() = {


    val list = pointService.getAll
    val jqGridPageView = new JqGridPageView[Point]()
    //    jqGridPageView.setMaxResults(list.getTotalCount)
    jqGridPageView.setRows(list)
    jqGridPageView.setPage(1)
    jqGridPageView.setTotal(list.size())
    jqGridPageView
  }


}
