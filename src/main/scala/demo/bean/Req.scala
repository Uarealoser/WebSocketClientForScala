package demo.bean

class Common {
  var app_id: String = ""

  def this(str: String) {
    this()
    this.app_id = str
  }

}

class Business {
  var keyword: String = ""
  var threshold: Int = 0
  var ent: String = "ivw"

  def this(s1: String, s2: Int) {
    this
    this.keyword = s1
    this.threshold = s2
  }

}

class DataReq {
  var status: Int = 0
  var format: String = "16000"
  var audio: String = ""
  var encoding: String = "raw"

  def this(s1: Int, s2: String) {
    this
    this.status = s1
    this.audio = s2
  }
}

@SerialVersionUID(43L) class Req {
  var common: Common = null
  var business: Business = null
  var data: DataReq = null

  def this(s1: Common, s2: Business, s3: DataReq) {
    this
    this.common = s1
    this.business = s2
    this.data = s3
  }
}

object Req {
  def getReq(app_id: String, keyword: String, threshold: Int, status: Int, audio: String): Req = {
    val req = new Req(new Common(app_id), new Business(keyword, threshold), new DataReq(status, audio))
    req
  }
}