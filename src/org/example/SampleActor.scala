package org.example

import akka.actor.{ Actor, ActorLogging }

object SampleActor {
  def infoMsg = "This is an info log"
  def warnMsg = "This is an warning log"
}

class SampleActor extends Actor with ActorLogging {
  def receive = {
    case "info" => log.info(SampleActor.infoMsg)
    case "warn" => log.warning(SampleActor.warnMsg)
    case "ping" => sender ! "pong"
  }
}