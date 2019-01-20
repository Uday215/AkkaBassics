package com.uday

import akka.actor.Actor
import akka.actor.ActorSystem
import com.typesafe.config.Config
import com.typesafe.config.ConfigFactory
import akka.actor.Props
import akka.routing.FromConfig
import akka.util.Timeout
import akka.pattern.ask
import scala.concurrent.duration._

class GroupCounterActor extends Actor {
  
  
  var counter=0
 override def receive = {
    case msg: String =>
      counter += 1
      println(s"Counter incremented by ${self.path}")

    case State => sender() ! counter
  }

  
}

  case object State

object GroupCounterActor extends App{
  
  val config=ConfigFactory.parseString("""
    | akka.actor.deployment {
    |  /groupRouter {
    |  router=round-robin-group
    |
    |  routees.paths=[
    |  user/counter1,
    |  user/counter2
    |]
    |}
    |}
""".stripMargin)

val system=ActorSystem("system",config)

system.actorOf(Props[GroupCounterActor], "counter1")
  system.actorOf(Props[GroupCounterActor], "counter2")
  
  
  val router = system.actorOf(FromConfig.props(Props[GroupCounterActor]), "groupRouter")

  for(i <- 1 to 4){
    router ! "increment by one"
  }

  implicit val timeout = Timeout(1000 seconds)
  import scala.concurrent.ExecutionContext.Implicits.global

  Thread.sleep(1000)

  val f = router ? State
  f.foreach(println _)

  
  
  

  }
  