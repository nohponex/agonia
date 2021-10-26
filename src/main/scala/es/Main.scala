package nohponex.agonia.es

import nohponex.agonia.es.events.*
import nohponex.agonia.es.game.{Game, dockGenerator, startAGameOf}
import nohponex.agonia.fp.cards.{Card, Rank, Suit}
import nohponex.agonia.fp.deck.{CardStack, Stack}
import nohponex.agonia.fp.gamestate.{Ace, Base7, Ended, GameState, Seven}
import nohponex.agonia.fp.player.{Player, Players}

import io.AnsiColor.*
import scala.io.StdIn.readLine

object Main {
  def main(args: Array[String]) = {
    var g = startAGameOf(3)

    while (!g.gameState.isInstanceOf[Ended]) {
      g.gameState match {
        case Ace(_, ofSuite) => println("Current card is: " + g.stackPair.peek() + " but of suit " + ofSuite)
        case _ => println("Current card is: " + g.stackPair.peek())
      }

      println()
      println("Current player is: " + g.players.Current())

      if g.CanFold() then {
        println(s"You can fold, type \"${UNDERLINED}f${RESET}old\"")
      }
      if g.gameState.isInstanceOf[Base7] then {
        val ToCount = g.gameState.asInstanceOf[Base7].ToDraw()
        println(s"You have either play 7 or to draw (${ToCount}), type \"${UNDERLINED}d${RESET}raw\"")
      } else if g.CanDraw() then {
        println(s"You can draw, type \"${UNDERLINED}d${RESET}raw\"")
      }

      println("Choose between:" )
      for (card, index) <- g.playerStacks(g.players.Current()).asInstanceOf[Stack].cards.zipWithIndex do {
        card.suit match {
          case Suit.Spades | Suit.Clubs => println(s"- ${UNDERLINED}${index}${RESET}) ${BLACK}${card}${RESET}")
          case Suit.Diamonds | Suit.Hearts => println(s"- ${UNDERLINED}${index}${RESET}) ${RED}${card}${RESET}")
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
    println("Game over! ")
  }
}
