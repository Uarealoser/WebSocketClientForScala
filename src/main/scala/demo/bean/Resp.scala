package demo.bean

class DataResp {
  var detail: Detail = null
  var rsw: String = ""
  var status: String = _

  def this(s1: Detail, s2: String, s3: String) {
    this
    this.detail = s1
    this.rsw = s2
    this.status = s3
  }
}

class Detail {
  var bos: Int = 0
  var id: Int = 0
  var score: Int = 0
  var eos: Int = 0
  var keyword: String = ""
  var ver: String = ""
  var sst: String = ""

  def this(s1: Int, s2: Int, s3: Int, s4: Int, s5: String, s6: String, s7: String) {
    this
    this.bos = s1
    this.id = s2
    this.score = s3
    this.eos = s4
    this.keyword = s5
    this.ver = s6
    this.sst = s7
  }
}

@SerialVersionUID(42L) class Resp {
  var data: DataResp = null
  var code: Int = 0
  var message: String = ""
  var sid: String = ""

  def this(s1: DataResp, s2: Int, s3: String, s4: String) {
    this
    this.data = s1
    this.code = s2
    this.message = s3
    this.sid = s4
  }

}
