// package com.zenon.pricetime
//
import org.scalatest._
import org.scalamock.scalatest.MockFactory
import org.scalatest.FlatSpec

import com.zenon.pricetime._

import org.http4s.{Request, Status, Uri}
import org.http4s._

class PriceTimeServiceSpec extends HttpServiceSpec {

  "PriceTime" should {

    val service = PriceTime()

    "Return by default a JSON value with a price given a valid ISODate and no query parameters" in {
      val request = new Request(uri = Uri(path = "/isodatetime/2015-07-04T05:00:00Z"))

      val response = service.run(request).run

     response.status should be (Status.Ok)
      val expectedResponse : String =  s""" {"price":1000} """.trim
       response.body.asString should be (expectedResponse)
    }

    "Return an JSON value with a price given a valid ISODate and json query parameter" in {
      val queryParam : Query = Query.fromString("any=json")
      val request = new Request(uri = Uri(path = "/isodatetime/2015-07-04T05:00:00Z", query=queryParam))

      val response = service.run(request).run

      response.status should be (Status.Ok)
      val expectedResponse : String =  s""" {"price":1000} """.trim
      response.body.asString should be (expectedResponse)
    }

    "Return an XML value with a price given a matching ISODate and xml query parameter" in {
      val queryParam : Query = Query.fromString("any=xml")
      val request = new Request(uri = Uri(path = "/isodatetime/2015-07-04T05:00:00Z", query=queryParam))

      val response = service.run(request).run

      response.status should be (Status.Ok)
      val expectedResponse : String   =  "<price>1000</price>"
      response.body.asString should be (expectedResponse)
    }

    "Return \"Unavailable\" give an ISODate that doesn't match the data set" in {
      val request = new Request(uri = Uri(path = "/isodatetime/2015-07-04T25:00:00Z"))

      val response = service.run(request).run

     response.status should be (Status.NotFound)
      val expectedResponse =  "Unavailable"
       response.body.asString should be (expectedResponse)
    }

    "Return \"Unavailable\" give a malformed ISODate" in {
      val request = new Request(uri = Uri(path = "/isodatetime/2015-07-04"))

      val response = service.run(request).run

     response.status should be (Status.NotFound)
      val expectedResponse =  "Unavailable"
       response.body.asString should be (expectedResponse)
    }
  }
}
