package com.zenon.pricetime.services

import com.zenon.pricetime.services.RateDeterminerService
import io.circe._
import java.time.ZonedDateTime
import org.http4s.circe._
import org.http4s.dsl._
import org.http4s.server._
import org.json4s.JsonAST._
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._
import scala.util.Try

object TimeParserService {

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
    if (zdt.getHour < 10) ("0" + zdt.getHour.toString + "00") else (zdt.getHour.toString + "00")
  }

  def maybeExtractDayOfWeek(dateInput: String) : Option[String] = {
    try {
      Some(extractDayOfWeek(dateInput))
    } catch {
      case e: Exception => None
    }
  }

  def parseTimeRange(timeRange: String) : (Int, Int) = {
    val timeBounds = timeRange.split("-").map(_.toInt)
    (timeBounds(0), timeBounds(1))
  }

  def parseDayRange(rateRange: String) : Array[String] = {
    rateRange.split(",")
  }
}
