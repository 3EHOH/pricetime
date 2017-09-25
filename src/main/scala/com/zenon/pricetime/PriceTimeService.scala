package com.zenon.pricetime

import com.zenon.pricetime.dto._
import com.zenon.pricetime.domain.ResourceReader._
import com.zenon.pricetime.services.TimeParserService.maybeExtractDayOfWeek
import com.zenon.pricetime.services.RateDeterminerService.getRate
import com.zenon.pricetime.services.ResponseFormatService.determineResponseFormat

import org.http4s.headers._
import org.http4s.HttpService
import org.http4s.Request

import org.http4s.dsl._
import org.http4s.server._
import org.json4s.JsonAST._
import org.json4s.Xml.toXml
import org.json4s.JsonDSL._
import org.json4s.native.JsonMethods._
import scala.util.Try

object PriceTimeService {

  def apply(): HttpService = service

  val service = HttpService {

    case GET -> Root / "isodatetime" / isodatetime :? queryParam =>

      val responseFormat: String = determineResponseFormat(queryParam)
      val dayOfWeek: Option[String] = maybeExtractDayOfWeek(isodatetime)
      val rateResponse: Int = getRate(dayOfWeek, isodatetime)

      if(rateResponse == -1) {
        NotFound("Unavailable")
      } else if(responseFormat == "xml"){
        Ok(toXml(JObject(JField("price", rateResponse))).toString)
      } else {
        Ok(compact(render("price", rateResponse)))
      }
  }
}
