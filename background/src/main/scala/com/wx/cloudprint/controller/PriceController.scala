package com.wx.cloudprint.controller

import javax.servlet.http.HttpServletRequest

import com.geekcattle.model.console.Admin
import com.geekcattle.service.console.AdminService
import com.geekcattle.service.member.MemberService
import com.geekcattle.util.ReturnUtil
import com.wx.cloudprint.dataservice.entity.Point
import com.wx.cloudprint.dataservice.service.{PointService, UserService}
import com.wx.cloudprint.dataservice.utils.JqGridPageView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

@Controller
@RequestMapping(value = Array("/console/price"))
class PriceController {
  @Autowired
  var pointService: PointService = _


  @RequestMapping(value = Array("/index"), method = Array(RequestMethod.GET))
  def index(): String = {
    "console/point/index"
  }

  @RequestMapping(value = Array("/from"), method = Array(RequestMethod.GET))
  def from(): String = {
    "console/point/from"
  }

  @RequestMapping(value = Array("/address"), method = Array(RequestMethod.GET))
  def address(): String = {
    "console/point/address"
  }

  @RequestMapping(value = Array("/search"), method = Array(RequestMethod.GET))
  @ResponseBody
  def search() = {


    val list = pointService.getAll
    val jqGridPageView = new JqGridPageView[Point]()
    //    jqGridPageView.setMaxResults(list.getTotalCount)
    jqGridPageView.setRows(list)
    jqGridPageView.setPage(1)
    jqGridPageView.setTotal(list.size())
    jqGridPageView
  }


  @RequestMapping(value = Array("/save"), method = Array(RequestMethod.POST))
  @ResponseBody
  def save(address: String, addressId: String, pointName: String, delivery_scope: String, phone: String, minCharge: Float, delivery_time: String) = {

    val point = new Point
    point.setPointName(pointName)
    point.setAddress(address)
    point.setAddress(addressId)
    point.setAddressId(addressId)
    point.setDelivery_scope(delivery_scope)
    point.setDelivery_time(delivery_time)
    point.setMinCharge(minCharge)
    point.setPhone(phone)
    pointService.add(point)
    //    addressSErvice.delete(id)

    ReturnUtil.Success("操作成功", null, null)


  }


  @Autowired
  var adminService:AdminService=_
  @RequestMapping(value = Array("/getPrice"), method = Array(RequestMethod.GET))
  @ResponseBody
  def getAllPoint(request: HttpServletRequest) = {
    val userName=request.getSession.getAttribute("currentUser").asInstanceOf[String]
    val userInfo = adminService.findByUsername(userName)
     val pointId=   userInfo.getPointId
    val point =pointService.get(pointId)
    point

  }


}
