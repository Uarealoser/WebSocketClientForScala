package demo.test

import java.io.IOException

import com.sun.org.slf4j.internal.LoggerFactory
import javax.websocket.server.{PathParam, ServerEndpoint}
import javax.websocket._

@ServerEndpoint(value = "/websocketTest") object Serve {
  def main(args: Array[String]): Unit = {

  }
}

@ServerEndpoint(value = "/websocketTest") class Serve {
  private val logger = LoggerFactory.getLogger(classOf[Serve])

  //连接时执行
  @OnOpen
  @throws[IOException]
  def onOpen(@PathParam("userId") userId: String, session: Session): Unit = {
    logger.debug("新连接：{}", userId)
  }
  //关闭时执行
  @OnClose def onClose(): Unit = {
    logger.debug("连接：{} 关闭")
  }
  //收到消息时执行
  @OnMessage
  @throws[IOException]
  def onMessage(message: String, session: Session): Unit = {
    logger.debug("收到用户{}的消息{}")


  }
  //连接错误时执行
  @OnError def onError(session: Session, error: Throwable): Unit = {
    logger.debug("用户id为：{}的连接发送错误")
    error.printStackTrace()
  }
}