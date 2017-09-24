package com.zenon.pricetime

import collection.mutable.Stack
import org.scalatest._
import com.zenon.pricetime.PriceTime._

class ExampleSpec extends FlatSpec with Matchers {

  "maybeExtractDayOfWeek(<param>)" should "return Option[String]" in {

    val testIsoDate = "2015-07-04T07:00:00Z" //Saturday
    maybeExtractDayOfWeek(testIsoDate) should be (Some("sat"))
  }

  it should "return None if the parameter is invalid" in {
    val invalidIsoDate = "2015-07-04"
    maybeExtractDayOfWeek(invalidIsoDate) should be (None)
  }
}
