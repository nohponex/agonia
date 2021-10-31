package nohponex.agonia.domain.model.ai

import nohponex.agonia.ai.MemorylessAI
import nohponex.agonia.domain.model.cards.{Card, Rank, Suit}
import org.scalatest.funsuite.AnyFunSuiteLike

class MemorylessAITest extends AnyFunSuiteLike {
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
