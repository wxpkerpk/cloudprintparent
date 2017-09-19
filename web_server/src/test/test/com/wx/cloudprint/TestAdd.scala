package com.wx.cloudprint

import java.util

import com.wx.cloudprint.dataservice.entity.Address
import com.wx.cloudprint.dataservice.service.{AddressService, PointService}
import com.wx.cloudprint.webserver.register.StartWebApplication
import junit.framework.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.springframework.test.context.junit4.SpringRunner

@RunWith(classOf[SpringRunner])
@SpringBootTest(classes = Array(classOf[StartWebApplication]))
class TestAdd  extends Assert {
  @Autowired
  var pointService:PointService=_
  @Autowired
  var addressService :AddressService=_





  @Test
  def add():Unit={

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
//    addressService.add(address)
//    addressService.add(address1)
//    addressService.add(address2)
//    addressService.add(address3)


  }
}
