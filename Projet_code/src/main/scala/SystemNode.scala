package org.example.mozartgame

import akka.actor.{Props, Actor, ActorRef, ActorSystem}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

case class Begin()
case class LogInfo(content: String)

class SystemNode(val nodeId: Int) extends Actor {

  val consoleDisplay = context.actorOf(Props[ConsoleDisplay], name = "consoleDisplay")
  val performer = context.actorOf(Props(new PerformerActor(self, nodeId)), name = "performerActor" + nodeId)
  val pulseActor = context.actorOf(Props(new PulseActor(nodeId)), name = "pulseActor" + nodeId)
  val healthMonitor = context.actorOf(Props(new HealthMonitor(self, nodeId)), name = "healthMonitor" + nodeId)

  // Planification des vérifications de santé
  val tickerCheck = context.system.scheduler.schedule(0.milliseconds, 2000.milliseconds, healthMonitor, CheckAll)
  val tickerMiss = context.system.scheduler.schedule(0.milliseconds, 3000.milliseconds, healthMonitor, IncrementMiss)

  def receive = {
    case Begin =>
      consoleDisplay ! LogInfo("=====================================================")
      consoleDisplay ! LogInfo(s"Node $nodeId created, path: ${self.path}")
      consoleDisplay ! LogInfo(s"Pulse actor for node $nodeId, path: ${pulseActor.path}")

    case LogInfo(content) =>
      consoleDisplay ! LogInfo(content)

    case AliveMsg(n) =>
      performer ! AliveMsg(n)

    case DeadMsg(n) =>
      performer ! DeadMsg(n)
  }
}
