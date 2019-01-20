package com.uday

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.Props
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


class PurchaseCarActor extends Actor with ActorLogging{
  var ref:ActorRef=null
  override def receive={
    
      
    case PurchaseCar =>
      ref=sender
      println(sender.path)
      println("request received for purchasing actor")
      context.actorOf(Props(classOf[CarAgenceyActor])).forward(PurchaseCar)
      
    case BrandNewCar=>
      ref ! "completed"
    
  }
  
}

class CarAgenceyActor extends Actor with ActorLogging{
  
  override def receive={
    
    
    case PurchaseCar =>
      println("we are in the car agencey  actor ")
      println(sender.path)
      sender ! "Fuck off"
    
  }
  
}
case object PurchaseCar
case object BrandNewCar
object ForwardPattern extends App{
  
  val system=ActorSystem("system")
  
  val refr=system.actorOf(Props(classOf[PurchaseCarActor]))
  
  implicit val timout=Timeout(1000 seconds)
  
  val futr=refr ? PurchaseCar
  
  futr.foreach(println)
  
  
}