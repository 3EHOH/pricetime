package com.zenon.pricetime

import org.scalatest._
import com.zenon.pricetime.services.RateDeterminerService._

class RateDeterminerServiceSpec extends FlatSpec with Matchers {

  "isRateInTimeRange" should "return a boolean value" in {
    isRateInTimeRange("0900", "0600-1800") should be (true)
  }

  "isRequestInDayRange" should "return a boolean value" in {
    isRequestInDayRange("tues", "mon,wed,fri") should be (false)
  }

  "getRate" should "return an int price if parameters are valid and in range" in {
    getRate(Some("wed"), "2015-07-05T05:00:00Z") should be (925)
  }

  it should "return -1 if parameters are invalid or out of range" in {
    getRate(None, "2015-07-05T34:00:00Z") should be (-1)
  }

}
