package com.zenon.pricetime

import io.circe._
import java.time.ZonedDateTime
import org.http4s._
import org.http4s.circe._
import org.http4s.dsl._
import org.http4s.server._
import org.json4s._
import org.json4s.native.JsonMethods._
import scala.io.Source
import scala.util.Try


object PriceTime {

  implicit val formats = org.json4s.DefaultFormats

  val service = HttpService {
    case GET -> Root / "isodatetime" / isodatetime =>
      val dayOfWeek: String = maybeExtractDayOfWeek(isodatetime).getOrElse("Invalid")

      var rateResponse: String = ""

      if(dayOfWeek.equals("Invalid")){
        rateResponse = "Not a valid input. Try again."
      } else {
        val rateData = niceFeedbackReadResource("sampledata.json")

        val parsedRates = parse(rateData.get).extract[Rates]

        val matchedRate = for (rate <- parsedRates.rates if
          (isRateInTimeRange(extractFormattedHour(isodatetime), rate.times)
          && isRequestInDayRange(extractDayOfWeek(isodatetime), rate.days))) yield rate

        if(matchedRate.isEmpty) {
          rateResponse = "unavailable"
        } else {
          rateResponse = matchedRate(0).price.toString
        }
      }

      Ok(Json.obj("message" -> Json.fromString(s"Your price: ${rateResponse}")))
  }


  def extractDayOfWeek(dateInput: String) : String = {
    abbrevDay(ZonedDateTime.parse(dateInput)
                           .getDayOfWeek
                           .toString
                           .toLowerCase)
  }

  def abbrevDay(day: String) : String = {
    day match {
      case "monday" => "mon"
      case "tuesday" => "tues"
      case "wednesday" => "wed"
      case "thursday" => "thurs"
      case "friday" => "fri"
      case "saturday" => "sat"
      case "sunday" => "sun"
    }
  }


  def extractFormattedHour(dateInput: String) : String = {
    val zdt: ZonedDateTime = ZonedDateTime.parse(dateInput)
    var formattedTime : String = ""
    if (zdt.getHour < 10) ("0" + zdt.getHour.toString + "00") else (zdt.getHour.toString + "00")
  }


  def maybeExtractDayOfWeek(dateInput: String) : Option[String] = {
    try {
      Some(extractDayOfWeek(dateInput))
    } catch {
      case e: Exception => None
    }
  }

  def niceFeedbackReadResource(resource: String): Option[String] = {
    try {
      //todo close source
      val resourceList : Some[String] = Some(Source.fromResource(resource).getLines.mkString)
      resourceList
    }
    catch {
      case e: Exception => println(e)
      None
    }
  }

  def isRateInTimeRange(queryRate: String, rateTimes: String) : Boolean = {
    val (rangeStart, rangeEnd) = parseTimeRange(rateTimes)
    (queryRate.toInt >= rangeStart && queryRate.toInt <= rangeEnd)
  }

  def isRequestInDayRange(queryDay: String, rateDays: String) : Boolean = {
    for(day <- parseDayRange(rateDays) if day == queryDay) return true
    return false
  }

  def parseTimeRange(timeRange: String) : (Int, Int) = {
    val timeBounds = timeRange.split("-").map(_.toInt)
    (timeBounds(0), timeBounds(1))
  }

  def parseDayRange(rateRange: String) : Array[String] = {
    rateRange.split(",")
  }

  case class Rate(days: String, times: String, price: Int)
  case class Rates(rates: List[Rate])
}
