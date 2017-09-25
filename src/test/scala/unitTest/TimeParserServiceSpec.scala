package com.zenon.pricetime

import org.scalatest._
import com.zenon.pricetime.services.TimeParserService._

class TimeParserServiceSpec extends FlatSpec with Matchers {

  "maybeExtractDayOfWeek" should "return Option[String]" in {
    val testIsoDate = "2015-07-04T07:00:00Z" //Saturday
    maybeExtractDayOfWeek(testIsoDate) should be (Some("sat"))
  }

  it should "return None if the parameter is invalid" in {
    val invalidIsoDate = "2015-07-04"
    maybeExtractDayOfWeek(invalidIsoDate) should be (None)
  }

  "extractDayOfWeek" should "return an abbreviated week day string" in {
    extractDayOfWeek("2015-07-05T05:00:00Z") should be ("sun")
  }

  "parseDayRange" should "split a comma-separated string into an array" in {
    parseDayRange("tues,wed,fri") should equal (Array("tues","wed","fri"))
  }

}
