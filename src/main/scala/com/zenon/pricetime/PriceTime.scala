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
    case GET -> Root / "isodatetime" / name =>
      val dayOfWeek: String = maybeExtractDayOfWeek(name)

      val test = niceFeedbackReadResource("sampledata.json")


      val parsedRates = parse(test.get).extract[Rates]

      val matchedRate = for (rate <- parsedRates.rates if rate.price.equals(2000))
      yield rate

      isRateInRange("", "")

      //print(parsed)

      Ok(Json.obj("message" -> Json.fromString(s"${name}, ${matchedRate}, ${dayOfWeek}, ${extractFormattedHour(name)}")))
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


  def maybeExtractDayOfWeek(dateInput: String) : String = {
    try {
      extractDayOfWeek(dateInput)
    } catch {
      case e: Exception => s"Exception caught: ${e}";
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

  def isRateInRange(queryRate: String, rateRange: String) : Boolean = {
    val (rangeStart, rangeEnd) = parseRateRange("0900-2100")
    (queryRate >= rangeStart && queryRange <= rangeEnd)
  }

  def parseRateRange(rateRange: String) : (Int, Int) = {
    val rateBounds = rateRange.split("-").map(_.toInt)
    (rateBounds(0), rateBounds(1))
  }

  case class Rate(days: String, times: String, price: Int)
  case class Rates(rates: List[Rate])
}
