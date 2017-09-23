package com.zenon.pricetime

import io.circe._
import org.http4s._
import org.http4s.circe._
import org.http4s.server._
import org.http4s.dsl._
import org.joda.time._

object HelloWorld {
  val service = HttpService {
    case GET -> Root / "isodatetime" / name =>
      val dayOfWeek: String = maybeExtractDayOfWeek(name)
      Ok(Json.obj("message" -> Json.fromString(s"Hello, ${name}, ${dayOfWeek}")))
  }

  def extractDayOfWeek(dateInput: String) : String = {
    val dateTime: DateTime = DateTime.parse(dateInput)
    dateTime.dayOfWeek.getAsShortText.toLowerCase
  }

  def maybeExtractDayOfWeek(dateInput: String) : String = {
    try {
      extractDayOfWeek(dateInput)
    } catch {
      case e: Exception => s"Exception caught: ${e}";
    }
  }
}
