package com.wx.cloudprint.controller

import com.fasterxml.jackson.databind.JsonNode
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.bind.annotation.{RequestMapping, ResponseBody, RestController}
import org.json4s.JsonAST.{JInt, JString, JValue}
import org.json4s.JsonDSL._
import org.json4s.jackson.JsonMethods._
@RestController
@RequestMapping(value = Array("/test"))
class Test {
  implicit def autoAsJsonNode(value: JValue): JsonNode = asJsonNode(value)

  @RequestMapping(value = Array("/"))
  @ResponseBody
  def test( param:String):JsonNode={
    ("data"->1)~("num"->3)
  }

}
