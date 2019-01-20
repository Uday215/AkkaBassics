package com.uday

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.ActorSystem
import akka.actor.Props
import scala.concurrent.Future
import akka.util.Timeout
import akka.pattern.ask
import akka.pattern.pipe
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


class FirstActor(second:ActorRef) extends Actor with ActorLogging{
  
  
  override def receive={
    
    case TestCard =>
      println("we are in the first Actor")
      val fut=Future{"This is the message"}
      fut pipeTo second
    case msg=>
       println(s"we are in the first Actor $msg")
      
  }
  
}

class SecondActor extends Actor with ActorLogging{
  override def receive={
    
    
    case msg=>
      println(s"we are in the Second Actor  $msg")
      sender !  msg
  }
}

case object TestCard
case object TestSecond

object PipePattern  extends App{
  
  val system=ActorSystem("system")
   implicit val timeout= Timeout(1000 seconds)
  
  
  
  
  val ref2 =system.actorOf(Props(classOf[SecondActor]))
  
  val ref =system.actorOf(Props(classOf[FirstActor],ref2))
  
  
  val result=ref ? TestCard
  
 // result.foreach(println)
  
}