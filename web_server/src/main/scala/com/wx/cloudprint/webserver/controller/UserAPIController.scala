package com.wx.cloudprint.webserver.controller

import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.wx.cloudprint.dataservice.entity.User
import com.wx.cloudprint.dataservice.service.UserService
import com.wx.cloudprint.webserver.service.SignService
import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods.asJsonNode
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, ResponseBody, RestController}

@RestController
@RequestMapping(value = Array("/API"))
class UserAPIController {
  @Autowired
  var signService:SignService=_

  @Autowired
  var userService:UserService=_
  implicit def autoAsJsonNode(value: JValue) = asJsonNode(value)

  @RequestMapping(value = Array("file/url"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody
  def login(request:HttpServletRequest, response:HttpServletResponse, username:String, password:String)={
    val result=signService.signin(username,password)
    val status=result("status")("code")
    val message=result("status")("message")
    status match {
      case "200" => {//登录成功
        val data=result("data")

        val userId=data("userId")
        var user=userService.findByShulianId(userId)
        if(user==null){
            val userName=data("userName")
          val nickName=data("nickName")
          val headPic=data("headPic")
          user=new User()
          user.setNickName(nickName)
          user.setShulianId(userId)
          user.setTel(userName)
          user.setHeadPic(headPic)
          userService.add(user)

        }
        request.getSession.setAttribute("user")

    }
    }


  }


}
