package demo.util

import java.io.{BufferedInputStream, FileInputStream}

class ReadFile {

}

object ReadFile {
  def read(str: String): Array[Byte] = {
    val bis = new BufferedInputStream(new FileInputStream(str))
    val bArray: Array[Byte] = Stream.continually(bis.read).takeWhile(-1 !=).map(_.toByte).toArray
    bArray
  }
}
