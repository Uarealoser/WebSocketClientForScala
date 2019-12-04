package demo.test

import java.util.Calendar
import java.util.concurrent.{ExecutorService, Executors}

import demo.common.IVW

object demoTest {
  def main(args: Array[String]): Unit = {
    //创建线程池
    val threadPool: ExecutorService = Executors.newFixedThreadPool(10)
    try {
      var data1=Calendar.getInstance.getTimeInMillis
      //提交5个线程
      for (i <- 1 to 20) {
        threadPool.execute(new ThreadDemo("thread" + i))
      }
    } finally {
      threadPool.shutdown()
    }
  }


}
class ThreadDemo(threadName:String) extends Runnable{
  override def run(){
        val ivw = new IVW
        val i1 = ivw.FilterData()
        println(this.threadName+"===="+i1)
  }
}