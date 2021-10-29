package nohponex.agonia.es

import nohponex.agonia.ai.RobotPlayer
import nohponex.agonia.es.events.*
import nohponex.agonia.es.game.{Game, deckGenerator}
import nohponex.agonia.fp.cards.{Card, Rank, Suit}
import nohponex.agonia.fp.deck.{CardStack, DeckGenerator, InjectedDeck, Stack}
import nohponex.agonia.fp.gamestate.{Ace, Base7, Ended, GameState, Seven}
import nohponex.agonia.fp.player.{Player, Players}

import io.AnsiColor.*
import scala.io.StdIn.readLine

object Main {
  def main(args: Array[String]) = {
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
        case _ => RobotPlayer.play(
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
  }

}
