package com.wx.cloudprint.controller

import javax.annotation.Resource
import javax.servlet.http.{HttpServletRequest, HttpServletResponse}

import com.wx.cloudprint.dataservice.service.ResService
import org.json4s.JsonAST.JValue
import org.json4s.jackson.JsonMethods.asJsonNode
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.{RequestMapping, RequestMethod, RequestParam, ResponseBody}

@RequestMapping(value = Array("/console/order"))
@Controller
class ImageController {
  @Resource
  private var resService: ResService = _


  var serverIp: String = _

  implicit def autoAsJsonNode(value: JValue) = asJsonNode(value)

  @RequestMapping(value = Array("/files"), method = Array(RequestMethod.GET, RequestMethod.POST))
  @ResponseBody def Preview(request: HttpServletRequest, response: HttpServletResponse, @RequestParam md5: String, @RequestParam size: String, @RequestParam row: Int, @RequestParam col: Int, @RequestParam(required = false) start: Int, end: Int) = {
    val res = resService.getByMD5(md5)
    if (res != null) {
      val urls = (start to end).map(x => genRestfulUrl(res.getHost, res.getPort, "/API/file/pic/get/preview?" + s"md5=$md5&size=$size&row=$row&col=$col&page=$x"))
      urls.toArray
    }
    else Array("")


  }

  private def genRestfulUrl(ip: String, port: String, prefix: String) = {
    val builder = new StringBuilder(80)
    builder.append("http://").append(ip).append(":").append(port).append(prefix)
    builder.toString
  }

}
