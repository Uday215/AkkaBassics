package com.uday

import akka.actor.Actor
import akka.actor.ActorRef
import akka.actor.Props

class SendingActor(ref:ActorRef) extends Actor{
  
  var counter=0
  override  def receive={
    
    
    case  msg=>
      counter+=1
      ref ! counter
      
  }
  
}

object SendingActor{
  
  def props(ref:ActorRef)=Props(classOf[SendingActor],ref)
}