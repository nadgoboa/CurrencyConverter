package currency

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsonFormat}

case class IncomingData[A](var data: List[A])

object IncomingDataProtocol extends DefaultJsonProtocol with SprayJsonSupport {
  implicit def IncomingDataFormats[A: JsonFormat] = jsonFormat1(IncomingData[A])
}