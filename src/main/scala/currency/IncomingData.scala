package currency

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.{DefaultJsonProtocol, JsonFormat}

case class Data[A](var data: List[A])

object DataProtocol extends DefaultJsonProtocol with SprayJsonSupport {
  implicit def DataFormats[A: JsonFormat] = jsonFormat1(Data[A])
}