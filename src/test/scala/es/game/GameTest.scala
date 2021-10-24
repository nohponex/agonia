package nohponex.agonia.es.game

import nohponex.agonia.fp.deck.{FixedDeck, DeckGenerator}
import nohponex.agonia.fp.player.Player

given dockGenerator:DeckGenerator = FixedDeck

class GameTest extends org.scalatest.funsuite.AnyFunSuite {
  test("startAGameOf2 should start a game where both players have 7 cards and one is open") {
    val g = startAGameOf2()

    assert(g.playerStacks(Player.Player1).length() == 7)
    assert(g.playerStacks(Player.Player2).length() == 7)
    assert(g.stackPair.peek() != null)
    assert(g.stackPair.stackLength() == 52-1-7-7)
  }
}
