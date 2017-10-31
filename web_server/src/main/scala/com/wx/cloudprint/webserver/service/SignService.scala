package com.wx.cloudprint.webserver.service

import org.json4s._
import org.json4s.jackson.JsonMethods._
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

import scalaj.http.Http
@Service
class SignService {
  implicit val formats = DefaultFormats

  @Value("${sign.signin.url}")
  var signinUrl:String=_
  @Value("${sign.register.url}")
  var registerUrl:String=_
  @Value("${sign.code.url}")
  var getCodeUrl:String=_

  @Value("${sign.getUserInfo.url}")
  var getUserInfo :String=_
  def signin(userName:String,password:String)={
    val body=Http(signinUrl).postForm(Seq("userName" -> userName, "password" -> password)).asString.body
    var map = parse(body)
    val message = convert((map \ "message").extract[String])
    val data = (map \ "data").extract[Map[String, String]]
    val code = (map \ "code").extract[String]
    (code, message, data)
  }

  @inline def convert(utfString: String): String = {
    //    val sb = new StringBuilder
    //    var i = -1
    //    var pos = 0
    //    while ( {
    //      (i = utfString.indexOf("\\u", pos)) != -1
    //    }) {
    //      sb.append(utfString.substring(pos, i))
    //      if (i + 5 < utfString.length) {
    //        pos = i + 6
    //        sb.append(Integer.parseInt(utfString.substring(i + 2, i + 6), 16).toChar)
    //      }
    //    }
    utfString
  }
  def getCode(tel:String)={
         val body=Http(getCodeUrl).postForm(Seq("userName" -> tel)).asString.body

    var map = parse(body).extract[Map[String, String]]
    map += ("message" -> convert(map("message")))
    map
  }
  def register(userName:String,password:String,code:String)={
    val body=Http(registerUrl).postForm(Seq("userName" -> userName, "password" -> password,"code"->code)).asString.body

    var map = parse(body).extract[Map[String, String]]
    map += ("message" -> convert(map("message")))
    map

  }


  def getUserInfo(userName:String)={

  }
}
