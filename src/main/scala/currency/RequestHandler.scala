package currency

import akka.actor.Actor
import currency.RequestHandler.{Incoming, Result}
import spray.json._

import scala.math.round

object RequestHandler {
  case class Incoming(data: String)
  case class Result(data: String)
}

import currency.IncomingDataProtocol._
import currency.OutcomingDataProtocol._
import currency.AnswerProtocol._
import currency.QueryProtocol._

class RequestHandler extends Actor {
  def compute(from: String, to: String, value: Double) : Double = {
    round(value*CurrentState.getRate(to)/CurrentState.getRate(from)*100)/100.0
  }
  def receive = {
    case Incoming(jsonStr) =>
      try{
        val jsonAst: JsValue = jsonStr.parseJson
        val input: IncomingData[Query] = jsonAst.convertTo[IncomingData[Query]]
        val out: OutcomingData[Answer] = OutcomingData(input.data.map(query => Answer(query.currencyFrom, query.currencyTo, query.valueFrom, compute(query.currencyFrom, query.currencyTo, query.valueFrom))),0,"No errors")
        sender ! Result(out.toJson.prettyPrint)
      } catch{
        case parsExcpt: spray.json.JsonParser.ParsingException => {
          val out: OutcomingData[Answer] = OutcomingData(List(),3,"Parsing Error: ".concat(parsExcpt.summary))
          sender ! Result(out.toJson.prettyPrint)
        }
        case desExcpt: spray.json.DeserializationException => {
          val out: OutcomingData[Answer] = OutcomingData(List(),2,"Deserialization Error: ".concat(desExcpt.msg))
          sender ! Result(out.toJson.prettyPrint)
        }
        case noElemExcpt: java.util.NoSuchElementException => {
          val out: OutcomingData[Answer] = OutcomingData(List(),1,"Undefiner Currency Error: ".concat(noElemExcpt.getMessage))
          sender ! Result(out.toJson.prettyPrint)
        }
        case unknown =>{
          val out: OutcomingData[Answer] = OutcomingData(List(),4,"Undefined Error")
          sender ! Result(out.toJson.prettyPrint)
        }
      }
      finally{
        context.stop(self)
      }

  }
}