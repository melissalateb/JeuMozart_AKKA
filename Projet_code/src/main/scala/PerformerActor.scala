package org.example.mozartgame

import akka.actor.{Props, Actor, ActorRef, ActorSelection, PoisonPill}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import ScoreDatabase._

case class InitiateLeader()
case class Farewell()
case class LeaderElected()
case class StartElection()
case class UpdateLeaderId(newId: Int)

class PerformerActor(systemNode: ActorRef, val actorId: Int) extends Actor {
  
  // Création des sous-acteurs : fournisseur de score et lecteur sonore
  val scoreProvider = context.actorOf(Props(new ScoreProvider(self)), "scoreProvider")
  val soundPlayer = context.actorOf(Props[SoundPlayer], name = "soundPlayer")
  
  var performers = new Array[ActorSelection](4)    // Adresses des autres performers
  var isAlive = new Array[Boolean](4)                // Statut de vie des performers
  var currentLeaderId = -1                          // Identifiant du chef courant
  var aliveCount = 0                                // Nombre de nodes vivants hormis soi-même

  isAlive(actorId) = true
  
  // Initialisation pour les autres nodes
  for(i <- 0 to 3 if i != actorId) {
    try {
      performers(i) = context.actorSelection("akka.tcp://MozartSystem" + i + "@127.0.0.1:600" + i + "/user/node" + i + "/performerActor" + i)
    } catch {
      case _: Throwable => println(s"[PerformerActor] Error: Unable to reach performer of node $i")
    }
    isAlive(i) = false
  }
  
  // Variables pour la gestion du chef d'orchestre
  val diceA = scala.util.Random
  val diceB = scala.util.Random
  var diceOutcome = 0
  val scheduler = context.system.scheduler
  val BASE_TIME = 1800.milliseconds
  val SOLO_TIME = 15000.milliseconds
  var aloneFlag = true

  def receive = {
    case InitiateLeader =>
      if (currentLeaderId == actorId) {
        diceOutcome = diceA.nextInt(5) + diceB.nextInt(5) + 2
        scoreProvider ! GetBar(diceOutcome)
      }

    case Bar(chordList) =>
      if (currentLeaderId == actorId) { // Si on est le chef
        if (aliveCount > 0) {
          var chosenId = -1
          var randSelector = scala.util.Random.nextInt(aliveCount) + 1
          for(i <- 0 to 3 if i != actorId) {
            if (isAlive(i)) {
              randSelector -= 1
              if (randSelector == 0) chosenId = i
            }
          }
          systemNode ! LogInfo(s"# Performer chosen: $chosenId")
          performers(chosenId) ! Bar(chordList)
          scheduler.scheduleOnce(BASE_TIME, self, InitiateLeader)
        } else {
          systemNode ! LogInfo("# No performer available, waiting...")
          aloneFlag = true
          scheduler.scheduleOnce(SOLO_TIME, self, Farewell)
        }
      } else { // Pour un simple performer
        systemNode ! LogInfo("=> Bar received, playing sound...")
        soundPlayer ! Bar(chordList)
      }

    case AliveMsg(n) =>
      aloneFlag = false
      if (!isAlive(n)) {
        systemNode ! LogInfo(s"=> Learned node $n is active!")
        if (aliveCount == 0 && currentLeaderId == actorId) {
          self ! InitiateLeader
        }
        isAlive(n) = true
        aliveCount += 1
        systemNode ! LogInfo(s"$aliveCount other nodes active now.")
      }
      if (currentLeaderId != -1) performers(n) ! UpdateLeaderId(currentLeaderId)

    case DeadMsg(n) =>
      systemNode ! LogInfo(s"=> Learned node $n is inactive...")
      if (isAlive(n)) {
        isAlive(n) = false
        aliveCount -= 1
        systemNode ! LogInfo(s"$aliveCount other nodes active now.")
      }
      if (currentLeaderId == n || currentLeaderId == -1) self ! StartElection

    case Farewell =>
      if (aloneFlag) {
        systemNode ! LogInfo("I'm feeling lonely... Farewell, world")
        context.system.terminate()
        System.exit(0)
      }

    case UpdateLeaderId(newId) =>
      if (newId != actorId && isAlive(newId)) currentLeaderId = newId

    case StartElection =>
      var elected = false
      for(i <- 0 to 3 if !elected) {
        if (isAlive(i)) {
          if (i == actorId) self ! LeaderElected
          else currentLeaderId = i
          elected = true
        }
      }

    case LeaderElected =>
      if (currentLeaderId != actorId) {
        currentLeaderId = actorId
        systemNode ! LogInfo("### JE SUIS LE CHEF HAHAHA ###")
        self ! InitiateLeader
      }
  }
}
