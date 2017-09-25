package com.zenon.pricetime.domain

import scala.io.Source

object ResourceReader {

  def readResource(resource: String): Option[String] = {
    try {
      Some(Source.fromResource(resource).getLines.mkString)
    }
    catch {
      case e: Exception => println(e)
      None
    }
  }
}
