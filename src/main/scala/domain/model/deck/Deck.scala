package nohponex.agonia.domain.model.deck

import nohponex.agonia.domain.model.cards.{Card, Rank, Suit}

import scala.collection.immutable.List

object Deck {
  def get(): List[Card] = List(
    Card(rank = Rank.Ace, suit = Suit.Hearts),
    Card(rank = Rank.Two, suit = Suit.Hearts),
    Card(rank = Rank.Three, suit = Suit.Hearts),
    Card(rank = Rank.Four, suit = Suit.Hearts),
    Card(rank = Rank.Five, suit = Suit.Hearts),
    Card(rank = Rank.Six, suit = Suit.Hearts),
    Card(rank = Rank.Seven, suit = Suit.Hearts),
    Card(rank = Rank.Eight, suit = Suit.Hearts),
    Card(rank = Rank.Nine, suit = Suit.Hearts),
    Card(rank = Rank.Ten, suit = Suit.Hearts),
    Card(rank = Rank.Jack, suit = Suit.Hearts),
    Card(rank = Rank.Queen, suit = Suit.Hearts),
    Card(rank = Rank.King, suit = Suit.Hearts),

    Card(rank = Rank.Ace, suit = Suit.Clubs),
    Card(rank = Rank.Two, suit = Suit.Clubs),
    Card(rank = Rank.Three, suit = Suit.Clubs),
    Card(rank = Rank.Four, suit = Suit.Clubs),
    Card(rank = Rank.Five, suit = Suit.Clubs),
    Card(rank = Rank.Six, suit = Suit.Clubs),
    Card(rank = Rank.Seven, suit = Suit.Clubs),
    Card(rank = Rank.Eight, suit = Suit.Clubs),
    Card(rank = Rank.Nine, suit = Suit.Clubs),
    Card(rank = Rank.Ten, suit = Suit.Clubs),
    Card(rank = Rank.Jack, suit = Suit.Clubs),
    Card(rank = Rank.Queen, suit = Suit.Clubs),
    Card(rank = Rank.King, suit = Suit.Clubs),
    
    Card(rank = Rank.Ace, suit = Suit.Spades),
    Card(rank = Rank.Two, suit = Suit.Spades),
    Card(rank = Rank.Three, suit = Suit.Spades),
    Card(rank = Rank.Four, suit = Suit.Spades),
    Card(rank = Rank.Five, suit = Suit.Spades),
    Card(rank = Rank.Six, suit = Suit.Spades),
    Card(rank = Rank.Seven, suit = Suit.Spades),
    Card(rank = Rank.Eight, suit = Suit.Spades),
    Card(rank = Rank.Nine, suit = Suit.Spades),
    Card(rank = Rank.Ten, suit = Suit.Spades),
    Card(rank = Rank.Jack, suit = Suit.Spades),
    Card(rank = Rank.Queen, suit = Suit.Spades),
    Card(rank = Rank.King, suit = Suit.Spades),
    
    Card(rank = Rank.Ace, suit = Suit.Diamonds),
    Card(rank = Rank.Two, suit = Suit.Diamonds),
    Card(rank = Rank.Three, suit = Suit.Diamonds),
    Card(rank = Rank.Four, suit = Suit.Diamonds),
    Card(rank = Rank.Five, suit = Suit.Diamonds),
    Card(rank = Rank.Six, suit = Suit.Diamonds),
    Card(rank = Rank.Seven, suit = Suit.Diamonds),
    Card(rank = Rank.Eight, suit = Suit.Diamonds),
    Card(rank = Rank.Nine, suit = Suit.Diamonds),
    Card(rank = Rank.Ten, suit = Suit.Diamonds),
    Card(rank = Rank.Jack, suit = Suit.Diamonds),
    Card(rank = Rank.Queen, suit = Suit.Diamonds),
    Card(rank = Rank.King, suit = Suit.Diamonds),
  )
}
