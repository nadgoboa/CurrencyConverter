package currency

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class Answer(currencyFrom: String, currencyTo: String, valueFrom: Double, valueTo: Double)

object AnswerProtocol extends DefaultJsonProtocol with SprayJsonSupport {
  implicit val AnswerFormats = jsonFormat4(Answer)
}