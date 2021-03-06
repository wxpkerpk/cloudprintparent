package com.wx.cloudprint.webserver.controller

import java.util.UUID
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.fasterxml.jackson.databind.JsonNode
import com.wx.cloudprint.dataservice.entity.User
import com.wx.cloudprint.dataservice.service.UserService
import com.wx.cloudprint.webserver.service.SignService
import org.json4s.DefaultFormats
import org.json4s.JsonAST.JValue
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods.asJsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(value = Array("/API"))
class UserAPIController extends BaseController {
  @Autowired
  var signService: SignService = _

  @Autowired
  var userService: UserService = _
  implicit val formats = DefaultFormats

  implicit def autoAsJsonNode(value: JValue) = asJsonNode(value)

  @RequestMapping(value = Array("/user/logining"), method = Array(RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS))
  @ResponseBody
  def login(@RequestBody userInfo: java.util.Map[String, String]): JsonNode = {
    val username = userInfo.getOrDefault("username", "").toString
    val password = userInfo.getOrDefault("password", "").toString
    val result = signService.signin(username, password)
    val status = result._1
    val message = result._2
    val info = status match {
      case "200" => //登录成功
        val data = result._3

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
        ("result" -> "OK") ~ ("info" -> (("nickname" -> user.getNickName) ~ ("uid" -> user.getId) ~ ("phone" -> user.getTel) ~ ("avatar" -> user.getHeadPic) ~ ("lastPoint" -> "") ~ ("access" -> "user")))
      case _ => ("result" -> "ERROR") ~ ("message" -> message)

    }

    info
  }

  @RequestMapping(value = Array("/user/login/state"), method = Array(RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS))
  @ResponseBody
  def getState(request: HttpServletRequest, response: HttpServletResponse): JsonNode = {
    val cookies = request.getCookies
    if (cookies != null) {
      cookies.foreach(cookies => {
        cookies.setSecure(false)
        cookies.setPath("/API")
        cookies.setMaxAge(9999999)
        cookies.setDomain(request.getScheme + "://" + request.getServerName + ":" + request.getServerPort)
        response.addCookie(cookies)
      })

    }

    if (request.getSession.getAttribute("user") == null) {
      ("result" -> "OK") ~ ("info" -> "LOGINING")
    } else {
      val user = getUser()
      ("result" -> "OK") ~ ("info" -> (("nickname" -> user.getNickName) ~ ("uid" -> user.getId) ~ ("phone" -> user.getTel) ~ ("avatar" -> user.getHeadPic) ~ ("lastPoint" -> "") ~ ("access" -> "user")))
    }


  }

  @RequestMapping(value = Array("/user/info"), method = Array(RequestMethod.PUT, RequestMethod.POST))
  @ResponseBody
  def update(nickname: String, // 用户昵称
             avatar: String, // 头像 url 或 base64 data url，为空时表示不更新头像
             address: String): JsonNode = {
    val user = getUser()
    val currentUser = userService.get(user.getId)
    currentUser.setHeadPic(avatar)
    currentUser.setNickName(nickname)
    currentUser.setAddress(address)
    userService.add(user)
    ("result" -> "ok") ~ ("info" -> "")
  }

  @RequestMapping(value = Array("/user/logout"), method = Array(RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS))
  @ResponseBody
  def logout(request: HttpServletRequest, response: HttpServletResponse): JsonNode = {
    request.getSession.removeAttribute("user")
    ("result" -> "ok") ~ ("info" -> "")
  }

  @RequestMapping(value = Array("/user/registerable"), method = Array(RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS))
  @ResponseBody
  def registerable(username: String): JsonNode = {
    val map = signService.signin(username, UUID.randomUUID().toString)
    val message = map._2
    if (message.contains("已注册")) {
      ("result" -> "ERROR") ~ ("message" -> "该用户已经注册过")


    } else {
      ("result" -> "OK") ~ ("info" -> "")

    }
  }

  @RequestMapping(value = Array("/user/signing"), method = Array(RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS))
  @ResponseBody
  def signing(@RequestBody userInfo: java.util.Map[String, String]): JsonNode = {
    val map = signService.register(userInfo.get("username").toString, userInfo.get("password").toString, userInfo.get("captcha").toString)

    val status = (map \ "code").extract[String]
    val message = (map \ "message").extract[String]
    val result = status match {
      case "200" => "OK"
      case _ => "ERROR"
    }

    ("result" -> result) ~ ("info" -> message) ~ ("message" -> message)


  }

  @RequestMapping(value = Array("/user/SMS/captcha"), method = Array(RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS))
  @ResponseBody
  def captcha(@RequestBody username: java.util.Map[String, Object]): JsonNode = {
    val map = signService.getCode(username.getOrDefault("username", "").toString)
    val status = map("code")
    val result = status match {
      case "200" => "OK"
      case _ => "ERROR"
    }

    ("result" -> result) ~ ("info" -> "")
  }
}
