package demo.common

class IVWConf {
    var ivw: IVW = _
}

object IVWConf {
    var IVW: IVW = {
        val ivw = new IVW
        ivw.appid = "4CC5779A"
        ivw.secrets = "aIoIvDpcrRKQ6sR9dbWTrlQBeXDwDEaN"
        ivw
    }
}
