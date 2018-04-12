package currency

import akka.actor.{Actor, Cancellable}
import currency.CurrencyResponseProtocol._
import spray.json._

import scala.concurrent.ExecutionContext.Implicits.global
class ScheduledOrderSynchronizer extends Actor {

  private val SYNC_ALL_ORDERS = "SYNC_ALL_ORDERS"

  private var scheduler: Cancellable = _

  override def preStart(): Unit = {
    import scala.concurrent.duration._
    scheduler = context.system.scheduler.schedule(
      initialDelay = 0 seconds,
      interval = 5 minutes,
      receiver = self,
      message = SYNC_ALL_ORDERS
    )
  }

  override def postStop(): Unit = {
    scheduler.cancel()
  }

  def get(url: String) = scala.io.Source.fromURL(url).mkString

  def receive = {
    case SYNC_ALL_ORDERS =>
      val result = scala.io.Source.fromURL("http://data.fixer.io/api/latest?access_key=a58789977ad5f971366950dd83199916").mkString
      val jsonAst = result.parseJson
      CurrentState.setRates(jsonAst.convertTo[CurrencyResponse].rates)
  }
}