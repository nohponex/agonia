package game

import Agonia.cards.Card
import Agonia.cards.Suit.Suit
import player.Players

sealed trait GameState (currentCard: Card) {
  def played(p: Players): Players = {
    this match {
      case Eight(_) => p
      case Nine(_) => p.Next().Next()
      case _ => p.Next()
    }
  }

  def isAllowed(c: Card): Boolean = {
    (this, c) match {
      case (Normal(_) | Eight(_) | Nine(_), Card(rank, suit)) => return currentCard.suit == suit || currentCard.rank == rank
      case (Ace(_, ofSuite), Card(rank, suit)) => return ofSuite == suit || currentCard.rank == rank
      case (Seven(_), _) => ???
    }

    false
  }
}

final case class Normal(currentCard: Card) extends GameState(currentCard)
final case class Ace(currentCard: Card, ofSuite: Suit) extends GameState(currentCard)
final case class Seven(currentCard: Card) extends GameState(currentCard)
final case class Seven2(currentCard: Card) extends GameState(currentCard)
final case class Seven3(currentCard: Card) extends GameState(currentCard)
final case class Eight(currentCard: Card) extends GameState(currentCard)
final case class Nine(currentCard: Card) extends GameState(currentCard)
