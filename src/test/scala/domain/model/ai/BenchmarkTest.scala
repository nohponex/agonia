package nohponex.agonia.domain.model.ai

import nohponex.agonia.domain.model.player.Player
import nohponex.agonia.domain.model.deck.NewShuflledStackFromDeck
import nohponex.agonia.domain.model.deck.DeckGenerator
import nohponex.agonia.domain.model.deck.Stack
import nohponex.agonia.domain.model.Game
import nohponex.agonia.domain.model.events.{PlayerEndedTurn, PlayerPlayedCard, PlayerPlayedCardAce}
import nohponex.agonia.domain.model.gamestate.Ended

given deckGenerator:DeckGenerator = NewShuflledStackFromDeck

class BenchmarkTest extends org.scalatest.funsuite.AnyFunSuiteLike {
    test("Benchmark") {
      val res = Range(0, 50).map(_ => {
        var g = Game.NewGame(2)

        while (!g.gameState.isInstanceOf[Ended]) {
          val imp = g.players.Current() match {
            case Player.Player1 => MemorylessAI.play
            case Player.Player2 => SmarterMemorylessAI.play
          }

          val event = imp(
            g,
            g.players.Current(),
            g.playerStack(g.players.Current()).asInstanceOf[Stack],
            g.gameState
          )
          g = g.play(event)
        }

        val winner:Player = g.ObservableEvents().last match {
          case PlayerEndedTurn(player) => player
          case PlayerPlayedCard(player, _) => player
          case PlayerPlayedCardAce(player, _, _) => player
          case _ => ???
        }
        winner
      }).groupBy(identity).view.mapValues(_.length).toMap

      println(res)
    }
}
