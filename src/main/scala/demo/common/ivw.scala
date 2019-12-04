package demo.common

import java.net.{URI, URLEncoder}
import java.text.SimpleDateFormat
import java.util.{Date, Locale, TimeZone}
import com.google.gson.Gson
import demo.bean.{Device, Req, Resp}
import demo.util.Client
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import org.apache.commons.codec.binary.Base64
import org.java_websocket.client.WebSocketClient

import scala.util.control.Breaks

class IVW {
  var origin: String = ""
  var url: String = "wss://ivw-api.xfyun.cn/v2/ivw"
  var appid: String = "5d896da5"
  var threshold: Int = 0
  var secrets: String = "aIoIvDpcrRKQ6sR9dbWTrlQBeXDwDEaN"
  var ws: Client = null

  def FilterData(): Int = try {
    if (Device.apply().threshold == 0) {
      return 0
    }
    Device.apply().`type` match {
      case "huifang" =>
      case "bihuan" =>
      case "original" =>
      case _ => return 0
    }
    val v: String = IVWConf.IVW.secrets
    if (v == "") {
      return 0
    }
    //val str=secrets.split(",",2)
    // val url=IVW.assembleAuthUrl(IVWConf.IVW.url,str(0),str(1))
    //鉴权
    val url = IVW.assembleAuthUrl(this.url, "529d144d0a6d825c2c47a9814af08927", "aIoIvDpcrRKQ6sR9dbWTrlQBeXDwDEaN")
    try {
      ws = new Client(url)
      ws.connectBlocking()
    } catch {
      case ex: Exception => {
        print(ex);
        return 0
      }
    }
    //创建pre对象
    try {
      val req1 = Req.getReq(Device.apply().appid, Device.apply().keyword, Device.apply().threshold,
        1, Device.apply().wav.toString)
      sendPreMessage(req1, ws)

      val req2 = Req.getReq(Device.apply().appid, Device.apply().keyword, Device.apply().threshold,
        2, Device.apply().wav.toString)
      sedEndMessage(req2, ws)
    } catch {
      case ex: Exception => print(ex); return 0
    }
    var resp: Resp = null
    val breaks = new Breaks
    while (true) {
      breaks.breakable {
        if (ws.retStr != null) {
          resp = new Gson().fromJson(ws.retStr, classOf[Resp])
        } else {
          breaks.break
        }
        if (resp.code != 0) {
          return 0
        }
        if (resp.data.status !="2") {
          breaks.break
        }
        if (resp.data.rsw != "1") {
          Device.apply().bypass = "false"
          return 0
        } else {
          Device.apply().bypass = "true"
        }
        Device.apply().start_ms = resp.data.detail.bos.toString
        Device.apply().end_ms = resp.data.detail.eos.toString
        Device.apply().selected_ncm = resp.data.detail.score.toString
        println("ws.retStr: "+ ws.retStr)
        return 1
      }
    }
    return 1
  } catch {
    case ex: Exception => print(ex); 0
  } finally {
    ws.close
  }

  def sendPreMessage(req: Req, client: Client): Unit = {
    val gson = new Gson
    val str = gson.toJson(req, classOf[Req])
    println("json: " + str)
    try {
      client.send(str)
    } catch {
      case ex: Exception => print(ex)
    }


  }

  def sedEndMessage(req: Req, client: Client) = {
    val gson = new Gson
    val str = gson.toJson(req, classOf[Req])
    try {
      client.send(str)
    } catch {
      case ex: Exception => print(ex)
    }
  }

}

object IVW {

  def assembleAuthUrl(hosturl: String, apiKey: String, apiSecret: String): String = try {
    val ul: URI = new URI(hosturl)
    val sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US)
    sdf.setTimeZone(TimeZone.getTimeZone("UTC"))
    //签名时间
    val sdfStr: String = sdf.format(new Date)
    val sginString = Array("host: " + ul.getHost, "date: " + sdfStr, "GET " + ul.getPath + " HTTP/1.1")
    //拼接签名结果
    val sgin = sginString(0) + "\n" + sginString(1) + "\n" + sginString(2)
    val sha = HmacWithShaTobase64(sgin, apiSecret)
    val authUrl = "api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"".format(apiKey,
      "hmac-sha256", "host date request-line", sha)
    val encoder = new Base64
    val authorization = encoder.encodeToString(authUrl.getBytes)
    val callurl = hosturl + "?" + "authorization" + "=" + authorization + "&" + "date" + "=" + URLEncoder.encode(sdfStr, "UTF-8") + "&" + "host" + "=" + URLEncoder.encode(ul.getHost, "UTF-8")
    callurl
  } catch {
    case ex: Exception => print(ex); null
  }

  //对签名进行sha256加密
  def HmacWithShaTobase64(sgin: String, apiSecret: String): String = try {
    val mac = Mac.getInstance("HmacSHA256")
    val secretKey = new SecretKeySpec(apiSecret.getBytes(), "HmacSHA256")
    mac.init(secretKey)
    val bytes: Array[Byte] = mac.doFinal(sgin.getBytes())
    val base64 = new Base64
    val str = base64.encodeAsString(bytes)
    str
  } catch {
    case ex: Exception => print(ex); null
  }

  def main(args: Array[String]): Unit = {
    val ivw = new IVW
    val i = ivw.FilterData()
    print(i)
  }
}