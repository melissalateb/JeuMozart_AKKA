package org.example.mozartgame

import akka.actor.{Props, Actor, ActorRef, ActorSystem, ActorSelection}

// Messages de contrôle de santé
case class PulseCheck()
case class CheckAll()
case class IncrementMiss()
case class AliveMsg(n: Int)
case class DeadMsg(n: Int)
case class HeartBeat(n: Int)

class HealthMonitor(systemNode: ActorRef, nodeId: Int) extends Actor {

  var heartRefs = new Array[ActorSelection](4)       // Références vers les acteurs de battement (coeurs)
  var aliveStatus = new Array[Boolean](4)              // Statut de vie des nodes
  var missCount = new Array[Int](4)                    // Compteur de non-réponses

  // Initialisation pour soi-même
  heartRefs(nodeId) = context.actorSelection("akka://MozartSystem" + nodeId + "/user/node" + nodeId + "/pulseActor" + nodeId)
  aliveStatus(nodeId) = true
  missCount(nodeId) = 0
  println(s"[HealthMonitor] Initialized for node $nodeId")

  // Initialisation pour les autres nodes
  for(i <- 0 to 3 if i != nodeId) {
    try {
      heartRefs(i) = context.actorSelection("akka.tcp://MozartSystem" + i + "@127.0.0.1:600" + i + "/user/node" + i + "/pulseActor" + i)
      println(s"[HealthMonitor] Retrieved remote heart address for node $i")
    } catch {
      case _: Throwable => println(s"[HealthMonitor] Error: Unable to access heart for node $i")
    }
    aliveStatus(i) = false
    missCount(i) = 0
  }

  def receive = {
    case IncrementMiss =>
      for(i <- 0 to 3 if i != nodeId) {
        missCount(i) += 1
        println(s"[HealthMonitor] Incrementing miss count for node $i")
        if (missCount(i) == 2) {
          systemNode ! LogInfo(s"Monitor - No heartbeat from node $i, awaiting response...")
          println(s"[HealthMonitor] Warning: No heartbeat from node $i, still waiting...")
        } else if (missCount(i) == 4) {
          aliveStatus(i) = false
          systemNode ! DeadMsg(i)
          println(s"[HealthMonitor] Declaring node $i as dead")
        }
      }

    case CheckAll =>
      // Envoyer un PulseCheck à tous les coeurs distants
      for(i <- 0 to 3 if i != nodeId) {
        heartRefs(i) ! PulseCheck
      }
      println("[HealthMonitor] Sent heartbeat check to all nodes")

    case HeartBeat(n) =>
      missCount(n) = 0
      aliveStatus(n) = true
      systemNode ! LogInfo(s"Heartbeat received from heart $n")
      systemNode ! AliveMsg(n)
      println(s"[HealthMonitor] Heartbeat received from node $n")
  }
}
