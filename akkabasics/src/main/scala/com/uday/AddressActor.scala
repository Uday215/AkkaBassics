package com.uday

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

class AddressFindActor extends Actor with ActorLogging{
  
  override def receive={
    
    case msg=>
      println("Sender Address:  "+sender.path)
      sender ! sender.path
      
      
  }
  
}


object AddressActor extends App{
  
  
  val system =ActorSystem("system")
  
  val ref=system.actorOf(Props(classOf[AddressFindActor]))
 implicit val timeout= Timeout(1000 seconds)
  
  println(ref.path)
  
  
  val res=ref ?  "Give my address"
   res.foreach(println)
}