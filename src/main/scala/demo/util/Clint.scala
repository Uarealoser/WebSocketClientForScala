package demo.util

import java.net.{URI, URISyntaxException}

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake

object Client {
}

class Client @throws[URISyntaxException]
(val url: String) extends WebSocketClient(new URI(url)) {
  var retStr: String = null

  override def onOpen(shake: ServerHandshake): Unit = {
    val it = shake.iterateHttpFields
    while ( {
      it.hasNext
    }) {
      val key = it.next
      println(key + ":" + shake.getFieldValue(key))
    }
  }


  override def onMessage(paramString: String): Unit = try {
    this.retStr = paramString
  } catch {
    case ex: Exception => print("onMessage Err")
  }

  override def onClose(paramInt: Int, paramString: String, paramBoolean: Boolean): Unit = {
    println("关闭")
  }

  override def onError(e: Exception): Unit = {
  }

}