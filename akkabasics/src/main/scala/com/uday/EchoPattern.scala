package com.uday

import akka.actor.ActorLogging
import akka.actor.Actor
import akka.actor.ActorSystem
import akka.actor.Props

class EchoActor extends Actor with ActorLogging{
  
  var counter=0
  
  override def receive={
    case msg=>
    counter+=1
    println(s"I received a message to something with actors $msg  ---$counter")
    
  
}

}



object EchoPattern  extends App{
  
  val system =ActorSystem("system")
  
  val ref =system.actorOf(Props(classOf[EchoActor]))
  
  ref ! "5"
  ref ! "6"
  
  val selectionActor=system.actorSelection(ref.path)
  
  
  selectionActor ! "7" 
  println(ref.path)
  println(selectionActor.pathString)
}