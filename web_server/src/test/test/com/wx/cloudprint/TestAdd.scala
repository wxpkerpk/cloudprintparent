package com.wx.cloudprint

import java.util

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.wx.cloudprint.dataservice.entity.{Address, Dispatch, Point}
import com.wx.cloudprint.dataservice.service.{AddressService, PointService}
import com.wx.cloudprint.webserver.controller.{color, money, price}
import com.wx.cloudprint.webserver.register.StartWebApplication
import com.wx.cloudprint.webserver.service.SignService
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
class TestAdd  {
  @Autowired
  var pointService:PointService=_
  @Autowired
  var addressService :AddressService=_


  @Autowired
  var gisnservice:SignService=_



  @Test
  def add():Unit={

  println(gisnservice.signin("123","123123"))
   println(gisnservice.getCode("18011302985"))
    println(gisnservice.register("`18011302985","666666pk","158711"))




  }
}
