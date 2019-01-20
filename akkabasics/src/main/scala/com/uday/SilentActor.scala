package com.uday

import akka.actor.Actor
import akka.actor.ActorRef
import com.uday.SilentActor.GetState

class SilentActor extends Actor{
  
  var counter =0
override def receive={
  case s:String=>
    counter+=1
    
    
  case GetState(ref)  =>
    ref ! counter
}
  
}

object SilentActor{
  
  case class GetState(ref:ActorRef)
}