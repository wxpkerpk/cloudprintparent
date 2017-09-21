package com.wx.cloudprint

import java.util

import com.google.gson.Gson
import com.wx.cloudprint.dataservice.entity.{Address, Dispatch, Point}
import com.wx.cloudprint.dataservice.service.{AddressService, PointService}
import com.wx.cloudprint.webserver.controller.{color, money, price}
import com.wx.cloudprint.webserver.register.StartWebApplication
import junit.framework.Assert
import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods.asJsonNode
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.junit4.SpringRunner
import org.json4s.JsonAST.{JInt, JString, JValue}
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
@RunWith(classOf[SpringRunner])
@SpringBootTest(classes = Array(classOf[StartWebApplication]))
class TestAdd  extends Assert {
  @Autowired
  var pointService:PointService=_
  @Autowired
  var addressService :AddressService=_





  @Test
  def add():Unit={

    import spray.json._
    import DefaultJsonProtocol._
//    val address = new Address
//    address.setId("1")
//    address.setName("北京市")
//    val address1 = new Address("xx区1", null)
//    address1.setId("2")
//
//    val address2 = new Address("xx区2", null)
//    address2.setId("3")
//
//
//    val addressList = new util.ArrayList[Address]
//    addressList.add(address1)
//    addressList.add(address2)
//
//    val address3 = new Address("直辖区", addressList)
//    address.getChildren.add(address3)
//    address3.setId("4")
//
//    //    addressService.add(address3)
//    val re=addressService.getRoot.toMap
//    println(new Gson().toJson(re))
def price2json(price:price)={
  implicit def autoAsJsonNode(value: JValue) = asJsonNode(value)

  val str=  (("caliper"->price.caliper)~("size"->price.size)~("money"->(
    "mono"->("oneside"->price.color.mono.oneside)~("duplex"->price.color.mono.duplex))~(
    "colorful"->("oneside"->price.color.mono.oneside)~("duplex"->price.color.mono.duplex))
    ))

  asJsonNode(str).toString

}

//    val str= price2json( price("A4","70g",  color(money(1.0f,2.0f),money(1.0f,2.0f))) )
//    val point=new Point()
//    point.setAddress("xxoo街")
//    point.setAddressId("2")
//    point.setDelivery_scope("整个学校周边3km以内")
//    point.setDelivery_time("9.00-19.00")
//    point.setImage("http://zyin-res.oss-cn-shenzhen.aliyuncs.com/static%2Fimages%2FATM.jpg")
//    point.setMessage("welcome")
//    point.setMinCharge(3f)
//    point.setPhone("110")
//    point.setPointName("有爱的打印店")
//    point.setPrice(Array(str).toJson.toString())
//    point.setStatus("运营中")
//    val dispatch=new Dispatch()
//    dispatch.setDescr("1234234")
//    dispatch.setDistributionCharge(300)
//    dispatch.setDistributionStart(500)
//    dispatch.setMaxPageCount(1000)
//    point.setDispatch(dispatch)
//    pointService.add(point)
    import scala.collection.JavaConverters._

    val point =pointService.getByAddressId("2")
     val s=point.asScala.map(x=>{
      var m =   string2jvalue(x.getPrice)
       m
    })
    println(point)


  }
}
