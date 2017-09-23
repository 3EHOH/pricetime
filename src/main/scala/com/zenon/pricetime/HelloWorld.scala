package com.zenon.pricetime

import io.circe._
import org.http4s._
import org.http4s.circe._
import org.http4s.server._
import org.http4s.dsl._
import org.joda.time._

object HelloWorld {
  val service = HttpService {
    case GET -> Root / "hello" / name =>
      val dateTime: DateTime = DateTime.parse(name)
      val dayOfWeek: String = dateTime.dayOfWeek().getAsShortText()
      Ok(Json.obj("message" -> Json.fromString(s"Hello, ${name}, ${dayOfWeek}")))
      //Ok(Json.obj("message" -> Json.fromString(s"Hello, ${name}")))
  }
}
