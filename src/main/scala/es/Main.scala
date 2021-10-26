package nohponex.agonia.es

import nohponex.agonia.es.events.*
import nohponex.agonia.es.game.{Game, dockGenerator, startAGameOf2}
import nohponex.agonia.fp.cards.{Card, Rank, Suit}
import nohponex.agonia.fp.deck.{CardStack, Stack}
import nohponex.agonia.fp.gamestate.{Ended, GameState, Ace}
import nohponex.agonia.fp.player.{Player, Players}
import io.AnsiColor._

import scala.io.StdIn.readLine

object Main {
  def main(args: Array[String]) = {
    var g = startAGameOf2()

    while (!g.gameState.isInstanceOf[Ended]) {
      g.gameState match {
        case Ace(_, ofSuite) => println("Current card is: " + g.stackPair.peek() + " but of suit " + ofSuite)
        case _ => println("Current card is: " + g.stackPair.peek())
      }

      println()
      println("Current player is: " + g.players.Current())

      if g.CanFold() then {
        println("You can fold, type \"fold\"")
      }
      if g.CanDraw() then {
        println("You can draw, type \"draw\"")
      }

      println("Choose between:" )
      for (card, index) <- g.playerStacks(g.players.Current()).asInstanceOf[Stack].c.zipWithIndex do {
        card.suit match {
          case Suit.Spades | Suit.Clubs => println(s"- ${index}) ${BLACK}${card}${RESET}")
          case Suit.Diamonds | Suit.Hearts => println(s"- ${index}) ${RED}${card}${RESET}")
        }

      }
      val event = playedCard(
        g,
        g.players.Current(),
        g.playerStacks(g.players.Current()).asInstanceOf[Stack],
        g.gameState
      )

      println("event:" + event)
      g = g.emit(event)
    }
    println("Game over! " + g.players.Current() + " won")
  }
}
