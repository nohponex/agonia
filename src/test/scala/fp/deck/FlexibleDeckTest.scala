package fp.deck

import nohponex.agonia.fp.cards.{Card, Rank, Suit}
import org.scalatest.funsuite.AnyFunSuiteLike

class FlexibleDeckTest extends AnyFunSuiteLike {

  test("test take two when 2 available") {
    val (d, cards) = FlexibleDeck(
      Some(Card(Rank.Ace, Suit.Spades)),
      Nil,
      List(
        Card(Rank.Ace, Suit.Hearts),
        Card(Rank.Ace, Suit.Diamonds)
      )
    ).take(2)

    assert(cards(0) == Card(Rank.Ace, Suit.Hearts))
    assert(cards(1) == Card(Rank.Ace, Suit.Diamonds))

    assert(d.available.length == 0)
  }

  test("test take three when 2 available") {
    val (d, cards) = FlexibleDeck(
      Some(Card(Rank.Ace, Suit.Spades)),
      List(
        Card(Rank.Ace, Suit.Spades),
        Card(Rank.Ace, Suit.Clubs)
      ),
      List(
        Card(Rank.Ace, Suit.Hearts),
        Card(Rank.Ace, Suit.Diamonds)
      )
    ).take(3)

    assert(cards(0) == Card(Rank.Ace, Suit.Hearts))
    assert(cards(1) == Card(Rank.Ace, Suit.Diamonds))
    assert(cards(2) == Card(Rank.Ace, Suit.Spades) || cards(2) == Card(Rank.Ace, Suit.Clubs))

    assert(d.played.length == 0)
    assert(d.available.length == 1)
    assert(d.available(0) == Card(Rank.Ace, Suit.Spades) || d.available(0) == Card(Rank.Ace, Suit.Clubs))
  }

  test("test remove") {
    val d = FlexibleDeck(
      Some(Card(Rank.Ace, Suit.Spades)),
      Nil,
      List(
        Card(Rank.Ace, Suit.Hearts),
        Card(Rank.Ace, Suit.Diamonds)
      )
    ).remove(List(Card(Rank.Ace, Suit.Hearts)))

    assert(d.available == List(Card(Rank.Ace, Suit.Diamonds)))
  }
}
