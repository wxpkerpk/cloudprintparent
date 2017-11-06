package com.wx.cloudprint.webserver.controller

import java.text.SimpleDateFormat
import javax.servlet.http.HttpServletRequest

import com.wx.cloudprint.dataservice.entity.User
import org.springframework.beans.factory.annotation.Autowired

import scala.util.Random

class BaseController {
  @Autowired var request: HttpServletRequest = _

  def getUser() = request.getSession.getAttribute("user").asInstanceOf[User]


}

object BaseController {
  val format = new SimpleDateFormat("yyyyMMddHHmmss")
  val random=new Random()

  def makeTimeId={
    System.currentTimeMillis() + (random.nextInt(90000) + 10000).toString
  }

  def main(args: Array[String]): Unit = {
    (0 to 20).foreach(x => println(makeTimeId))
  }

}