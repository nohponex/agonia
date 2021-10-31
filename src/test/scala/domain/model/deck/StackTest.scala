package nohponex.agonia.domain.model.deck

import nohponex.agonia.domain.model.cards.{Card, Rank, Suit}

class StackTest extends org.scalatest.funsuite.AnyFunSuite {
  test("when EmptyStack push") {
    val c = Card(rank = Rank.Ace, suit = Suit.Spades)
    val s = EmptyStack().push(c)
    assert(c == s.peek())
  }

  test("push pushes card on top") {
    val c = Card(rank = Rank.Ace, suit = Suit.Spades)
    val s = Stack(List(Card(rank = Rank.Queen, suit = Suit.Hearts))).push(c)
    assert(c == s.peek())
  }

  test("Remove") {
    val cardToRemove = Card(rank = Rank.Ace, suit = Suit.Hearts)
    val s = Stack(List(
      Card(rank = Rank.Queen, suit = Suit.Hearts),
      cardToRemove,
      Card(rank = Rank.King, suit = Suit.Hearts),
    )).remove(cardToRemove)

    assert(s.length() == 2)
    assert(s == Stack(List(
      Card(rank = Rank.Queen, suit = Suit.Hearts),
      Card(rank = Rank.King, suit = Suit.Hearts),
    )))
  }
}
