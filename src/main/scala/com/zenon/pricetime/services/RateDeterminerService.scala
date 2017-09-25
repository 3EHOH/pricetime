package com.zenon.pricetime.services

import com.zenon.pricetime.services.TimeParserService._
import com.zenon.pricetime.dto.Rates
import com.zenon.pricetime.domain.ResourceReader.readResource

import io.circe._
import org.http4s.circe._
import org.http4s.dsl._
import org.http4s.server._
import org.json4s.JsonAST._
import org.json4s.Xml.toXml
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._
import scala.util.Try

object RateDeterminerService {

  implicit val formats = org.json4s.DefaultFormats

  def isRateInTimeRange(queriedTime: String, rateTimes: String) : Boolean = {
    val (rangeStart, rangeEnd) = parseTimeRange(rateTimes)
    (queriedTime.toInt >= rangeStart && queriedTime.toInt <= rangeEnd)
  }

  def isRequestInDayRange(queryDay: String, rateDays: String) : Boolean = {
    for(day <- parseDayRange(rateDays) if day == queryDay) return true
    return false
  }

  def getRate(dayOfWeek: Option[String], queryDate: String) : Int = {
    var rate : Int = -1 //default
    if(!dayOfWeek.equals(None)){
      val rateData = readResource("sampledata.json")
      val parsedRates = parse(rateData.get).extract[Rates]

      val matchedRate = for (rate <- parsedRates.rates if
        (isRateInTimeRange(extractFormattedHour(queryDate), rate.times)
        && isRequestInDayRange(extractDayOfWeek(queryDate), rate.days))) yield rate

      if(!matchedRate.isEmpty) {
        rate = matchedRate.head.price
      }
    }
    rate
  }
}
