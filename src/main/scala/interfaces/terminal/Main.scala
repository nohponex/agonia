package nohponex.agonia.interfaces.terminal

import nohponex.agonia.ai.MemorylessAI
import nohponex.agonia.domain.model.Game
import nohponex.agonia.domain.model.cards.{Card, Rank, Suit}
import nohponex.agonia.domain.model.deck.*
import nohponex.agonia.domain.model.events.*
import nohponex.agonia.domain.model.gamestate.*
import nohponex.agonia.domain.model.player.{Player, Players}

import scala.io.AnsiColor.*
import scala.io.StdIn.readLine

object Main {
  def main(args: Array[String]) = {
    given deckGenerator:DeckGenerator = NewShuflledStackFromDeck
    var g = Game.NewGame(2)

    while (!g.gameState.isInstanceOf[Ended]) {
      g.gameState match {
        case Ace(card, ofSuite) => println(s"Current card is: ${formatedCard(card)} but of suit ${formatedSuit(ofSuite)}")
        case _ => println(s"Current card is: ${formatedCard(g.peek())}")
      }

      println()
      println("Current player is: " + g.players.Current())

      val event = g.players.Current() match {
        case Player.Player1  => play(
          g,
          g.players.Current(),
          g.playerStack(g.players.Current()).asInstanceOf[Stack],
          g.gameState
        )
        case _ => MemorylessAI.play(
          g,
          g.players.Current(),
          g.playerStack(g.players.Current()).asInstanceOf[Stack],
          g.gameState
        )
      }

      println("event:" + event)
      g = g.play(event)
    }
    println("Game over! ")
    println("Game events replay: ")
    g.ObservableEvents().foreach(println)
  }

}
