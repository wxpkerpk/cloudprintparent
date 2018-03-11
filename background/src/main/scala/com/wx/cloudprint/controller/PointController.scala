package com.wx.cloudprint.controller

import com.geekcattle.util.ReturnUtil
import com.wx.cloudprint.dataservice.entity.{Address, Dispatch, Point}
import com.wx.cloudprint.dataservice.service.{AddressService, PointService}
import com.wx.cloudprint.dataservice.utils.JqGridPageView
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
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
  def from(model: Model): String = {
    val point = new Point
    val dispatch = new Dispatch
    point.setDispatch(dispatch)
    model.addAttribute("point", point)

    "console/point/from"
  }

  @RequestMapping(value = Array("/edit"), method = Array(RequestMethod.GET))
  def edit(id: String
           , model: Model): String = {
    val point = pointService.get(id)
    model.addAttribute("point", point)

    "console/point/from"
  }

  @RequestMapping(value = Array("/delete"), method = Array(RequestMethod.GET))
  @ResponseBody
  def delete(ids: Array[String]
            ) = {
    ids.foreach(
        pointService.delete(_)

    )
    ReturnUtil.Success("删除成功" +
      "", null, null)

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

  @RequestMapping(value = Array("/getAddress"), method = Array(RequestMethod.GET))
  @ResponseBody
  def getAddress(id: String) = {


    if (id != null && !id.equals("")) {
      val address = addressSErvice.get(id)
      val parent1 = address.getParent.getId
      val parent2 = address.getParent.getParent.getId
      Array(parent2, parent1)
    } else {
      Array.empty[String]
    }

  }


  @RequestMapping(value = Array("/deleteAddress"), method = Array(RequestMethod.GET))
  @ResponseBody
  def deleteAddress(id: String) = {

    addressSErvice.delete(id)

    ReturnUtil.Success("操作成功", null, null)

  }

  @RequestMapping(value = Array("/save"), method = Array(RequestMethod.POST))
  @ResponseBody
  def save(id: String, address: String, addressId: String, pointName: String,
           delivery_scope: String, phone: String, minCharge: Float, delivery_time: String, distributionCharge: Float) = {

    val point = new Point
    if (id != null && !id.equals(""))
      point.setId(id)
    point.setPointName(pointName)
    point.setAddress(address)
    point.setAddressId(addressId)
    point.setDelivery_scope(delivery_scope)
    point.setDelivery_time(delivery_time)
    val dispath = new Dispatch
    dispath.setDistributionStart(minCharge)
    dispath.setDistributionStart(distributionCharge)
    point.setDispatch(dispath)
    point.setMinCharge(minCharge)
    point.setPhone(phone)
    pointService.add(point)
    //    addressSErvice.delete(id)

    ReturnUtil.Success("操作成功", null, null)


  }


  @RequestMapping(value = Array("/editAddress"), method = Array(RequestMethod.GET))
  @ResponseBody
  def edit(id: String, name: String) = {

    val address = addressSErvice.get(id)
    address.setName(name)
    addressSErvice.add(address)

    ReturnUtil.Success("操作成功", null, null)

  }

  @RequestMapping(value = Array("/getAllPoint"), method = Array(RequestMethod.GET))
  @ResponseBody
  def getAllPoint() = pointService.getAll



}
