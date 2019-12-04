package plugins.client

import demo.common.IVW
import org.apache.jmeter.protocol.java.sampler.{AbstractJavaSamplerClient, JavaSamplerContext}
import org.apache.jmeter.samplers.SampleResult

class DemoJavaSamplerClient extends AbstractJavaSamplerClient{
  override def runTest(javaSamplerContext: JavaSamplerContext): SampleResult ={
    var resultData: Int = -1
    val result = new SampleResult
    result.setSampleLabel("请求开始")
    try{
      result.sampleStart()//开始统计时间
      val ivw = new IVW
      val i1 = ivw.FilterData()
      resultData=i1
      result.setSuccessful(true)
    }catch {
      case ex:Exception=>{
        result.setSuccessful(false)
        println(ex)
        result
      }
    }finally {
      result.sampleEnd
    }
    result
  }
}
