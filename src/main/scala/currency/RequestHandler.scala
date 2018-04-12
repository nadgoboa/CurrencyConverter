package currency

import akka.actor.Actor
import currency.RequestHandler.{Incoming, Result}
import spray.json._
import scala.math.round

object RequestHandler {
  case class Incoming(data: String)
  case class Result(data: String)
}

import currency.AnswerProtocol._
import currency.DataProtocol._
import currency.QueryProtocol._

class RequestHandler extends Actor {
  def compute(from: String, to: String, value: Double) : Double = {
    round(value*CurrentState.getRate(to)/CurrentState.getRate(from)*100)/100.0
  }
  def receive = {
    case Incoming(jsonStr) =>
      val jsonAst: JsValue = jsonStr.parseJson
      val input: Data[Query] = jsonAst.convertTo[Data[Query]]
      val out = Data(input.data.map(query => Answer(query.currencyFrom, query.currencyTo, query.valueFrom, compute(query.currencyFrom, query.currencyTo, query.valueFrom))))
      sender ! Result(out.toJson.prettyPrint)
      context.stop(self)
  }
}