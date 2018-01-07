package com.wx.cloudprint.controller

import com.wx.cloudprint.dataservice.entity.{Address, Point}
import com.wx.cloudprint.dataservice.service.{AddressService, PointService}
import com.wx.cloudprint.dataservice.utils.JqGridPageView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody}

@Controller
@RequestMapping(value = Array("/console/point"))
class PointController {
  @Autowired
  var pointService: PointService = _
  @Autowired
  var addressSErvice: AddressService = _

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

  @RequestMapping(value = Array("/addAddress"), method = Array(RequestMethod.GET))
  @ResponseBody
  def addAddress(id: String, name: String, parentId: String) = {
    val address = new Address()
    address.setId(id)
    address.setName(name)
    if (!parentId.equals("0")) {
      val parentAddress = addressSErvice.get(parentId)
      addressSErvice.add(address)
      parentAddress.addChild(address)
      addressSErvice.add(parentAddress)
    } else {
      address.setIs_root(true)
      addressSErvice.add(address)
    }
    "成功"

  }

  @RequestMapping(value = Array("/getAllAddress"), method = Array(RequestMethod.GET))
  @ResponseBody
  def getAllAddress(id: String) = {


    addressSErvice.findAll()

  }

  @RequestMapping(value = Array("/deleteAddress"), method = Array(RequestMethod.GET))
  @ResponseBody
  def delete(id: String): String = {

    addressSErvice.delete(id)

    "成功"

  }

  @RequestMapping(value = Array("/save"), method = Array(RequestMethod.POST))
  @ResponseBody
  def save(address: String, addressId: String, pointName: String, delivery_scope: String, phone: String, minCharge: Float, delivery_time: String): String = {

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

    "成功"

  }


  @RequestMapping(value = Array("/editAddress"), method = Array(RequestMethod.GET))
  @ResponseBody
  def edit(id: String, name: String): String = {

    val address = addressSErvice.get(id)
    address.setName(name)
    addressSErvice.add(address)

    "成功"

  }

  @RequestMapping(value = Array("/getAllPoint"), method = Array(RequestMethod.GET))
  @ResponseBody
  def getAllPoint() = pointService.getAll



}
