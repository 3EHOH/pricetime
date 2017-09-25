package com.zenon.pricetime

import org.http4s.HttpService
import org.http4s.Request
import org.http4s.dsl._
import org.http4s.server._
import com.codahale.metrics.MetricRegistry
import com.codahale.metrics._
import com.codahale.metrics.json.MetricsModule
import com.fasterxml.jackson.databind.ObjectMapper
import com.codahale.metrics.MetricRegistry
import java.util.concurrent.TimeUnit

object MetricsService {

  def apply(): HttpService = service

  val metrics = new MetricRegistry()
  val priceTimeMeter : Meter = new Meter
  val priceTimeTimer : Timer = new Timer
  metrics.register("priceTimeMeter", priceTimeMeter);
  metrics.register("priceTimeTimer", priceTimeTimer);
  val mapper = new ObjectMapper()
    .registerModule(new MetricsModule(TimeUnit.SECONDS, TimeUnit.SECONDS, true))

  val writer = mapper.writerWithDefaultPrettyPrinter()

  val service =  HttpService {
    case GET -> Root / "metrics" =>
      Ok(writer.writeValueAsString(metrics))
  }
}
