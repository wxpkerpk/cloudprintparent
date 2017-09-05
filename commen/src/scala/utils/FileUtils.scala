package utils

import java.io.{File, FileInputStream, FileOutputStream}

import scala.io.Source

class FileUtils {

}
object FileUtils{

  def readFile(path:String)={
    val file = new File(path)
    val fileInputStream = new FileInputStream(file)
    val len = fileInputStream.available
    val pdfBytes = new Array[Byte](len)
    fileInputStream.read(pdfBytes)
    fileInputStream.close()
     pdfBytes
  }
  def writeFile(data:Array[Byte],path:String)={
    val file =new File(path)
    val fileOutputStream=new FileOutputStream(file)
    fileOutputStream.write(data)
    fileOutputStream.flush()
    fileOutputStream.close()
  }

  def main(args: Array[String]): Unit = {
      println("1")
  }

}
