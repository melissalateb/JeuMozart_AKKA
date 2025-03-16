package org.example.mozartgame

import com.typesafe.config.ConfigFactory
import akka.actor._

object MozartApp {
  def main(args: Array[String]): Unit = {
    if(args.size != 1) {
      println("Syntax error: usage: run <number>")
      sys.exit(1)
    }
    val id = args(0).toInt
    if(id < 0 || id > 3) {
      println("Error: <number> must be between 0 and 3")
      sys.exit(1)
    }
    val system = ActorSystem("MozartSystem" + id, ConfigFactory.load().getConfig("system" + id))
    val node = system.actorOf(Props(new SystemNode(id)), "node" + id)
    node ! Begin
  }
}
