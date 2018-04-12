package currency

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class CurrencyResponse(success: Boolean, timestamp: Int, base: String, date: String, rates: Map[String, Double])

object CurrencyResponseProtocol extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val CurrencyResponseFormats = jsonFormat5(CurrencyResponse)
}