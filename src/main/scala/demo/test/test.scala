package demo.test

import com.google.gson.Gson
import demo.bean.{Business, Common, DataReq, Req}

object test {
  def main(args: Array[String]): Unit = {
    //    val a:String="hello,world"
    //    val strings = a.split(",",2)
    //    print(strings(0)+" 111"+strings(1))


    val gson = new Gson
    val req = new Req
    var common: Common = new Common
    var dataReq: DataReq = new DataReq
    var business: Business = new Business

    dataReq.audio = "wav"
    dataReq.encoding = "raw"
    dataReq.format = "16000"
    dataReq.status = 1

    common.app_id = "1"
    business.ent = "ivw"
    business.keyword = "123"
    business.threshold = 123

    req.data = dataReq
    req.common = common
    req.business = business

    val str = gson.toJson(req, classOf[Req])
    val o = gson.fromJson(str, classOf[Req])
    print(str)
    print(o.isInstanceOf[Req])
  }
}
