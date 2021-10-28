package nohponex.agonia.fp.deck

import nohponex.agonia.fp.cards.{Card, Rank, Suit}
import org.scalatest.funsuite.AnyFunSuiteLike

class StackPairTest extends AnyFunSuiteLike {
  test("when stack is empty used fp.cards are switched") {
    val stack = EmptyStack()

    val firstCard = Card(rank = Rank.Ten, suit = Suit.Spades)
    val cardToPush = Card(rank = Rank.Ace, suit = Suit.Diamonds)

    var (pair, card) = StackPair(stack, Stack(List(cardToPush)))
      .play(firstCard)
      .take1()

    assert(card == cardToPush)
    assert(pair.peek() == firstCard)
  }

  test("take(2)") {
    val stack = EmptyStack()

    val cardToPush = Card(rank = Rank.Ace, suit = Suit.Diamonds)
    val playedCard = Card(rank = Rank.Ten, suit = Suit.Spades)
    val lastCard = Card(rank = Rank.King, suit = Suit.Diamonds)

    val (_, cards) = StackPair(stack, Stack(List(cardToPush)))
      .play(playedCard)
      .play(lastCard)
      .take(2)

    assert((cards(0) == playedCard && cards(1) == cardToPush) || (cards(0) == cardToPush &&  cards(1) == playedCard))
  }
}
