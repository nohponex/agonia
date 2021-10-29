package ai

import nohponex.agonia.ai.RobotPlayer
import nohponex.agonia.fp.cards.{Card, Rank, Suit}
import org.scalatest.funsuite.AnyFunSuiteLike

class RobotPlayerTest extends AnyFunSuiteLike {

  test("testPreferedSuit") {
    assert(
      RobotPlayer.preferedSuit(
        List(
          Card(Rank.Ace, Suit.Hearts),
          Card(Rank.Ace, Suit.Hearts),
          Card(Rank.Ace, Suit.Hearts),
          Card(Rank.Ace, Suit.Spades),
        )
      ) == Suit.Hearts
    )
  }
}
