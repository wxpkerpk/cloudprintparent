package com.wx.cloudprint.webserver.controller

import java.text.SimpleDateFormat
import javax.servlet.http.HttpServletRequest

import com.wx.cloudprint.dataservice.entity.User
import org.springframework.beans.factory.annotation.Autowired

import scala.util.Random

class BaseController {
  @Autowired var request: HttpServletRequest = _
  def getUser()= request.getSession.getAttribute("User").asInstanceOf[User]


}
object BaseController{
  val format = new SimpleDateFormat("yyyyMMddHHmmss")
  val random=new Random()

  def makeTimeId={
   System.currentTimeMillis()+ random.nextInt(89999)+10000.toString
  }

}