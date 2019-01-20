package com.uday

import akka.actor.Actor
import akka.actor.DeadLetter
import akka.actor.ActorSystem
import akka.actor.Props
import akka.actor.PoisonPill


class DeadLetterMonitor extends Actor{
  
  override def receive={
    case d:DeadLetter=>println(d)
  }
}

class SucideActor extends Actor{
  override def receive={
    case _=>
  }
}
object DeadLetterSample extends App{
  val system =ActorSystem("system")
  val ref =system.actorOf(Props(classOf[SucideActor]))
  
  ref ! PoisonPill
  ref ! "Message goes to deadletters"

  
  
  val listener =system.actorOf(Props(classOf[DeadLetterMonitor]))
  
  system.eventStream.subscribe(listener, classOf[DeadLetter])
  
}