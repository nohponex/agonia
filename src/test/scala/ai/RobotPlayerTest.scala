package ai

import nohponex.agonia.ai.MemorylessAI
import nohponex.agonia.fp.cards.{Card, Rank, Suit}
import org.scalatest.funsuite.AnyFunSuiteLike

class RobotPlayerTest extends AnyFunSuiteLike {
  test("testPreferedSuit on empty list") {
    MemorylessAI.preferedSuit(Nil)
  }

  test("testPreferedSuit") {
    assert(
      MemorylessAI.preferedSuit(
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
