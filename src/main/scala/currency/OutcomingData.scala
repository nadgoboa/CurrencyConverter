package currency

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsonFormat}

case class OutcomingData[A](var data: List[A], var errorCode: Int, var errorMessage: String)

object OutcomingDataProtocol extends DefaultJsonProtocol with SprayJsonSupport {
  implicit def OutcomingDataFormats[A: JsonFormat] = jsonFormat3(OutcomingData[A])
}