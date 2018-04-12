package currency

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class Query(currencyFrom: String, currencyTo: String, valueFrom: Double)
object QueryProtocol extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val QueryFormats = jsonFormat3(Query)
}