package demo.bean

import demo.util.ReadFile
import org.apache.commons.codec.binary.Base64

class Device {
  var deviceid: String = ""
  var wav: String = _
  var `type`: String = ""
  var appid: String = ""
  var ncm: String = ""
  var selected_ncm: String = ""
  var sid: String = ""
  var mlf_sy: String = ""
  var keyword: String = ""
  var valid: String = ""
  var threshold: Int = 0
  var sign: String = ""
  var resid: String = ""
  var end_ms: String = ""
  var bypass: String = ""
  var start_ms: String = ""
  var label: String = ""
  var client_ip: String = ""
}

//构造一个单例对象
object Device {
  def apply(): Device = {
    val device = new Device()
    device.appid = "5d896da5"
    device.`type` = "original"
    device.ncm = "1000"
    device.keyword = "wu2 sheng4 cheng2"
    val encoder = new Base64()
    device.wav = encoder.encodeToString(ReadFile.read("D:\\demo\\wu.pcm"))
    device.threshold = 1
    device
  }
}
