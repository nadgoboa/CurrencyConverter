package currency

import akka.actor.{ActorSystem, Props}
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives.{entity, _}
import akka.pattern.ask
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.scalalogging._
import org.slf4j.LoggerFactory

import scala.concurrent.duration._
import scala.io.StdIn

object RequestReceiver extends App  {

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  private val logger = Logger(LoggerFactory.getLogger(this.getClass))
  logger.info(s"\nlocalhost:8080 ON")
  println("press ENTER to switch OFF")



  val route =
    pathEndOrSingleSlash {
      post {
        entity(as[String]) { entity =>
          logger.info(s"request:\n".concat(entity))
          implicit val timeout = Timeout(2 seconds)
          val actor = system.actorOf(Props[RequestHandler])
          onSuccess((actor ? RequestHandler.Incoming(entity)).mapTo[RequestHandler.Result]) { result =>
            logger.info(s"answer:\n".concat(result.data))
            complete(result.data)
          }
        }
      }
    }
  val syncronizer =  system.actorOf(Props(new ScheduledOrderSynchronizer), name = "orderSynchronizer")


  Http().bindAndHandle(route, "localhost", 8080)
  StdIn.readLine()
  system.terminate()
  logger.info(s"\nlocalhost:8080 OFF")
}