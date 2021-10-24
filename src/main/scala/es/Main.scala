package nohponex.agonia.es

import nohponex.agonia.es.game.Game
import nohponex.agonia.es.game.{startAGameOf2, dockGenerator}
import nohponex.agonia.fp.gamestate.Ended
import nohponex.agonia.es.events.*

object Main {
  def main(args: Array[String]) = {
    var g = startAGameOf2()

    while (!g.gameState.isInstanceOf[Ended]) {
      println("Current card is: " + g.stackPair.peek())
      val played = nohponex.agonia.fp.game.play(
        g.gameState,
        g.players,
        g.playerStacks,
      )

      g = g.emit(PlayerPlayedCard(g.players.Current(), played))
    }
    println("Game over!")
  }
}
