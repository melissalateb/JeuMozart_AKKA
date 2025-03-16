package org.example.mozartgame

import akka.actor.{Props, Actor, ActorRef, ActorSystem}
import ScoreDatabase._

class ScoreProvider(performer: ActorRef) extends Actor {
  val scoreDB = context.actorOf(Props[ScoreDatabase], "scoreDatabase")
  
  def receive = {
    case GetBar(diceResult) =>
      scoreDB ! GetBar(diceResult)
    case Bar(chords) =>
      performer ! Bar(chords)
  }
}
