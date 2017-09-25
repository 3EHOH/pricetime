package com.zenon.pricetime

import org.scalatest._
import com.zenon.pricetime.services.ResponseFormatService._

class ResponseFormatterServiceSpec extends FlatSpec with Matchers {

  "setResponseFormat" should "return \"json\" by default if no query param is supplied" in {
    setResponseFormat(None) should be ("json")
  }

  it should "return \"xml\" if \"xml\" is supplied as a query param" in {
    setResponseFormat(Some("xml")) should be ("xml")
  }

  it should "return \"json\" if any other value is supplied as a query param" in {
    setResponseFormat(Some("dummyVal")) should be ("json")
  }
}
