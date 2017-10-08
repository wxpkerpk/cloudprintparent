package com.wx.cloudprint.webserver.controller

import java.util.UUID
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.wx.cloudprint.dataservice.entity.User
import com.wx.cloudprint.dataservice.service.UserService
import com.wx.cloudprint.webserver.service.SignService
import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods.asJsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody, RestController}
import org.json4s.JsonAST.{JInt, JString, JValue}
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
import spray.json._
import DefaultJsonProtocol._
import com.fasterxml.jackson.databind.JsonNode

@RestController
@RequestMapping(value = Array("/API"))
class UserAPIController {
  @Autowired
  var signService: SignService = _

  @Autowired
  var userService: UserService = _

  implicit def autoAsJsonNode(value: JValue) = asJsonNode(value)

  @RequestMapping(value = Array("/user/login"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody
  def login(request: HttpServletRequest, response: HttpServletResponse, username: String, password: String): JsonNode = {
    val result = signService.signin(username, password)
    val status = result("status")("code")
    val message = result("status")("message")
    val info = status match {
      case "200" => //登录成功
        val data = result("data")

        val userId = data("userId")
        var user = userService.findByShulianId(userId)
        if (user == null) {
          val userName = data("userName")
          val nickName = data("nickName")
          val headPic = data("headPic")
          user = new User()
          user.setNickName(nickName)
          user.setShulianId(userId)
          user.setTel(userName)
          user.setHeadPic(headPic)
          userService.add(user)

        }
        request.getSession.setAttribute("user", user)
        ("result" -> "OK") ~ ("info" -> (("nickname" -> user.getNickName) ~ ("uid" -> user.getId) ~ ("phone" -> user.getTel) ~ ("avatar" -> user.getHeadPic) ~ ("lastPoint" -> "")))
      case "300" => ("result" -> "OK") ~ ("info" -> "用户不存在")
      case _ => ("result" -> "OK") ~ ("info" -> "账号或密码错误")

    }

    info
  }

  @RequestMapping(value = Array("/user/login/state"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody
  def getState(request: HttpServletRequest, response: HttpServletResponse): JsonNode = {
    if (request.getSession.getAttribute("user") == null) {
      ("result" -> "OK") ~ ("info" -> "LOGINING")
    } else ("result" -> "OK") ~ ("info" -> "NOT_LOGINING")

  }

  @RequestMapping(value = Array("/user/logout"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody
  def logout(request: HttpServletRequest, response: HttpServletResponse): JsonNode = {
    request.getSession.removeAttribute("user")
    ("result" -> "ok") ~ ("info" -> "")
  }
  @RequestMapping(value = Array("/user/registerable"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody
  def registerable(username:String):JsonNode={
    val map=signService.signin(username,UUID.randomUUID().toString)
    val status = map("status")("code")
    val result= status match {
      case "300"=>"error"
      case _ =>"OK"
    }
    ("result" -> result) ~ ("info" -> "")
  }
  @RequestMapping(value = Array("/user/SMS/captcha"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody
  def captcha(username:String)= {
    val map = signService.getCode(username)
    val status = map("status")("code")
    val result = status match {
      case "200" => "OK"
      case _ => "error"
    }

    ("result" -> result) ~ ("info" -> "")
  }
}
