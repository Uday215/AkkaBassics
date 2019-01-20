package com.uday

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global




class TeaSeller extends Actor with ActorLogging{
  
  override def receive={
    
    case Tea=>
      println("order received for Tea ,kindly enjoy your tea")
      sender ! "Tea is Ready to Enjoy"
    case Samosa=>
      println("order received for Samosa ,kindly enjoy your Samosa")
     sender ! "Samosa is Ready to Enjoy"
    case Cigerrate=>
      println("Smoking is ingerious to Health")
       sender ! " Ready to Die"
      
    
  }
  
}

case object Tea
case object Samosa
case object Cigerrate
object TellPattern extends App{
  
  val system=ActorSystem("system")
  
  def props=Props(classOf[TeaSeller])
  
  val ref1=system.actorOf(props)
 implicit  val timeout=  Timeout(1000 seconds)
 
  
  val futr=ref1 ? "going"
  futr.foreach(println)
  val r= ref1 ? Samosa
  r.foreach(println)
  val d=  ref1 ? Cigerrate
   d.foreach(println)
}