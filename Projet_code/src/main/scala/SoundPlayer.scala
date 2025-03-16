package org.example.mozartgame

import math._
import javax.sound.midi._
import javax.sound.midi.ShortMessage._
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import akka.actor.{Props, Actor, ActorRef, ActorSystem}
import ScoreDatabase._

object SoundPlayer {
  case class MidiEvent(pitch: Int, duration: Int, volume: Int, at: Int)
  val synthInfo = MidiSystem.getMidiDeviceInfo().filter(_.getName == "Gervill").headOption
  val midiDevice = synthInfo.map(MidiSystem.getMidiDevice).getOrElse {
    println("[ERROR] Synthesizer not found.")
    sys.exit(1)
  }
  val receiver = midiDevice.getReceiver()

  def noteOn(pitch: Int, volume: Int, channel: Int): Unit = {
    val msg = new ShortMessage
    msg.setMessage(NOTE_ON, channel, pitch, volume)
    receiver.send(msg, -1)
  }
  def noteOff(pitch: Int, channel: Int): Unit = {
    val msg = new ShortMessage
    msg.setMessage(NOTE_ON, channel, pitch, 0)
    receiver.send(msg, -1)
  }
}

class SoundPlayer extends Actor {
  import ScoreDatabase._
  import SoundPlayer._
  midiDevice.open()

  def receive = {
    case Bar(chords) =>
      chords.foreach { chord =>
        chord match {
          case Chord(at, tones) =>
            tones.foreach {
              case Tone(p, duration, volume) =>
                self ! MidiEvent(p, duration, volume, at)
            }
        }
      }
    case MidiEvent(p, duration, volume, at) =>
      context.system.scheduler.scheduleOnce(at.milliseconds)(noteOn(p, volume, 10))
      context.system.scheduler.scheduleOnce((at + duration).milliseconds)(noteOff(p, 10))
  }
}
