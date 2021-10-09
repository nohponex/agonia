package deck

import Agonia.cards.{Card, Rank, Suit}

class StackTest extends org.scalatest.funsuite.AnyFunSuite {
  test("take1 returns only first card") {
    var s = Stack(List(
      Card(rank = Rank.Ace, suit = Suit.Clubs),
      Card(rank = Rank.Ace, suit = Suit.Diamonds),
      Card(rank = Rank.Ace, suit = Suit.Hearts),
      Card(rank = Rank.Ace, suit = Suit.Spades),
      Card(rank = Rank.Two, suit = Suit.Clubs),
    ))
    assert(5 == s.length())

    val (s1, card) = s.take1()

    assert(card == Card(rank = Rank.Ace, suit = Suit.Clubs))
    assert(4 == s1.length())
  }

  test("test(n) takes n first cards") {
    var s = Stack(List(
      Card(rank = Rank.Ace, suit = Suit.Clubs),
      Card(rank = Rank.Ace, suit = Suit.Diamonds),
      Card(rank = Rank.Ace, suit = Suit.Hearts),
      Card(rank = Rank.Ace, suit = Suit.Spades),
      Card(rank = Rank.Two, suit = Suit.Clubs),
    ))
    assert(5 == s.length())

    val (s1, cards) = s.take(3)
    assert(2 == s1.length())
    assert(3 == cards.length)

    assert(cards(0) == Card(rank = Rank.Ace, suit = Suit.Clubs))
    assert(cards(1) == Card(rank = Rank.Ace, suit = Suit.Diamonds))
    assert(cards(2) == Card(rank = Rank.Ace, suit = Suit.Hearts))

    s1 match {
      case _: deck.EmptyStack => assert(false)
      case ss: deck.Stack => assert(ss.peek() == Card(rank = Rank.Ace, suit = Suit.Spades))
    }
  }

  test("test(52) should return empty") {
    val s = NewShuflledStackFromDeck()
    assert(52 === s.length())

    val res = s.take(52)
    assert(res._1 == EmptyStack())
    assert(52 == res._2.length)
  }

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

  test("doing 52 take1 will empty a stack from deck") {
    var deck1: CardStack = NewShuflledStackFromDeck()
    var otherDeck: CardStack = EmptyStack()

    for( _ <- 0 until 52){
      deck1 match {
        case _: deck.EmptyStack => assert(false)

        case dd: deck.Stack => {
          val (d1, card) = dd.take1()
          deck1 = d1
          otherDeck = otherDeck.push(card)
        }
      }
    }

    assert(deck1 == EmptyStack())
    assert(52 == otherDeck.length())
  }
}
