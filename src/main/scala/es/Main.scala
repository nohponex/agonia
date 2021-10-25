package nohponex.agonia.es

import nohponex.agonia.es.events.*
import nohponex.agonia.es.game.{Game, dockGenerator, startAGameOf2}
import nohponex.agonia.fp.cards.Card
import nohponex.agonia.fp.deck.{CardStack, Stack}
import nohponex.agonia.fp.gamestate.{Ended, GameState}
import nohponex.agonia.fp.player.{Player, Players}

import scala.io.StdIn.readLine

object Main {
  def main(args: Array[String]) = {
    var g = startAGameOf2()

    while (!g.gameState.isInstanceOf[Ended]) {
      println("Current card is: " + g.stackPair.peek())
      println()
      println("Current player is: " + g.players.Current())

      if g.CanFold() then {
        println("You can draw, type \"fold\"")
      }
      if g.CanDraw() then {
        println("You can draw, type \"draw\"")
      }

      println("Choose between:" )
      for (card, index) <- g.playerStacks(g.players.Current()).asInstanceOf[Stack].c.zipWithIndex do {
        println(s"- ${index}) ${card}")
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

def playedCard(g : Game, player: Player, stack: Stack, state: GameState): Event = {
  while(true) {
    val read = readLine()

    if read == "draw" && g.CanDraw() then {
      return PlayerDrewCard(player)
    }
    if read == "fold" && g.CanFold() then {
      return PlayerFolded(player)
    }

    read.toIntOption match {
      case None => {}
      case Some(asInt) => {
        if asInt >= 0 && asInt < stack.length() then
          val played = stack.c(asInt)
          if state.isAllowed(played) then
            return PlayerPlayedCard(player, played)
          //todo case ace ask suit
          else
            println(s"$played is not allowed now")
        else
          println("out of index")
      }
    }
  }

  //nop
  return PlayerFolded(player)
}
