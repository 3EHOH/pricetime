package com.zenon.pricetime

import io.circe._
import org.http4s._
import org.http4s.circe._
import org.http4s.server._
import org.http4s.dsl._
//import org.joda.time._
import java.time.ZonedDateTime
import java.util.Date
import java.text.SimpleDateFormat

object HelloWorld {
  val service = HttpService {
    case GET -> Root / "isodatetime" / name =>
      val dayOfWeek: String = maybeExtractDayOfWeek(name)

      val zonedDateTime: ZonedDateTime = ZonedDateTime.parse(name)

      Ok(Json.obj("message" -> Json.fromString(s"${name}, ${dayOfWeek}, ${extractFormattedHour(name)}")))
  }

  def extractDayOfWeek(dateInput: String) : String = {
    val zdt: ZonedDateTime = ZonedDateTime.parse(dateInput)
    zdt.getDayOfWeek.toString.toLowerCase
    //zdt.getHour.toString
    //val day: SimpleDateFormat = new SimpleDateFormat("HHmm")
    //timeFormat.format(Date.from(zdt.toInstant())).toString
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
}
