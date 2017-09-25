package com.zenon.pricetime

import com.zenon.pricetime.AppServer
import org.scalatest.{FlatSpec, Matchers}

class AppServerSpec extends FlatSpec with Matchers{

  "A server" should "start" in {
    AppServer.server(List())
  }
}
