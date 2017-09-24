package com.zenon.pricetime

import io.circe._
import java.time.ZonedDateTime
import org.http4s.headers._
import org.http4s.HttpService
import org.http4s.Request
import org.http4s.circe._
import org.http4s.dsl._
import org.http4s.server._
import org.json4s.JsonAST._
import org.json4s.Xml.toXml
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._
import scala.io.Source
import scala.util.Try


object PriceTime {

  def apply(): HttpService = service

  implicit val formats = org.json4s.DefaultFormats

  val service = HttpService {

    case GET -> Root / "isodatetime" / isodatetime :? queryParam =>

      var responseFormat: String = determineResponseFormat(queryParam)
      val dayOfWeek: Option[String] = maybeExtractDayOfWeek(isodatetime)
      var rateResponse: Int = getRate(dayOfWeek, isodatetime)

      if(rateResponse == -1) {
        NotFound("Unavailable")
      } else if(responseFormat == "xml"){
        Ok(toXml(JObject(JField("price", rateResponse))).toString)
      } else {
        Ok(compact(render("price", rateResponse)))
      }
  }

def setResponseFormat(queryParam: Option[String]) : String = {
    queryParam match {
      case Some(paramVal) if paramVal == "xml" => paramVal
      case _ => "json"
    }
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

  def determineResponseFormat(queryParam: Map[String,Seq[String]]) : String = {
    var firstQueryParamVal : Option[String] = None
    if(!queryParam.isEmpty){
      val (queryParamKey, queryParamVals) = queryParam.head
      firstQueryParamVal = queryParamVals.headOption
    }
    setResponseFormat(firstQueryParamVal)
  }

  def getRate(dayOfWeek: Option[String], queryDate: String) : Int = {
    var rate : Int = -1 //default
    if(!dayOfWeek.equals(None)){
      val rateData = niceFeedbackReadResource("sampledata.json")
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

  case class Rate(days: String, times: String, price: Int)
  case class Rates(rates: List[Rate])
}
