package nohponex.agonia.fp.deck

import nohponex.agonia.fp.cards.{Card, Rank, Suit}
import org.scalatest.funsuite.AnyFunSuiteLike

class StackPairTest extends AnyFunSuiteLike {
  test("when stack is empty used fp.cards are switched") {
    val stack = Stack(List(
      Card(rank = Rank.Ace, suit = Suit.Clubs),
    ))

    val cardToPush = Card(rank = Rank.Ace, suit = Suit.Diamonds)

    var (pair, card) = StackPair(stack).push(cardToPush).take1()._1.take1()

    assert(cardToPush == card)
  }
}
