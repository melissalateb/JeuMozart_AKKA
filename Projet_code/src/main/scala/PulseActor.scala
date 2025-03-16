package org.example.mozartgame

import akka.actor._

class PulseActor(val nodeId: Int) extends Actor {
  def receive = {
    case PulseCheck =>
      sender() ! HeartBeat(nodeId)
  }
}
