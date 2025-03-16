package org.example.mozartgame

import akka.actor._

class ConsoleDisplay extends Actor {
  def receive = {
    case LogInfo(content) =>
      println(content)
  }
}
