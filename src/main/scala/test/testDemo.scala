package test
import util.control.Breaks._
object testDemo {
  def main(args: Array[String]): Unit = {
      var i=0
    breakable{
      while (i<10){
        i+=1
          if(i==2)break
          else println(i)
        }
        println("hello")
      }
  }

}
