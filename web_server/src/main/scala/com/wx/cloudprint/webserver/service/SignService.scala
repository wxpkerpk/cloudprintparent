package com.wx.cloudprint.webserver.service

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import scalaj.http.Http
import scala.util.parsing.json.JSON
@Service
class SignService {
  @Value("${sign.signin.url}")
  var signinUrl:String=_
  @Value("${sign.register.url}")
  var registerUrl:String=_
  @Value("${sign.code.url}")
  var getCodeUrl:String=_
  def signin(userName:String,password:String)={
    val body=Http(signinUrl).postForm(Seq("userName" -> userName, "password" -> password)).asString.body
    val map=JSON.parseFull(body).get.asInstanceOf[Map[String,Map[String,String]]]
    map("status")


  }
  def getCode(tel:String)={
         val body=Http(getCodeUrl).postForm(Seq("userName" -> tel)).asString.body
    val map=JSON.parseFull(body).get.asInstanceOf[Map[String,Map[String,String]]]
    map("status")
  }
  def register(userName:String,password:String,code:String)={
    val body=Http(registerUrl).postForm(Seq("userName" -> userName, "password" -> password,"code"->code)).asString.body
    val map=JSON.parseFull(body).get.asInstanceOf[Map[String,Map[String,String]]]
    map("status")

  }
}
