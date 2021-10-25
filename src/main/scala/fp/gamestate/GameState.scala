package nohponex.agonia.fp.gamestate

import nohponex.agonia.fp.cards.{Card, Suit, Rank}
import nohponex.agonia.fp.game.*
import nohponex.agonia.fp.player.Players

sealed trait GameState (currentCard: Card) {
  def played(p: Players): Players = {
    this match {
      case _ => p.Next()
    }
  }

  def isAllowed(c: Card): Boolean = {
    (this, c) match {
      case (Normal(_), Card(rank, suit)) => return currentCard.suit == suit || currentCard.rank == rank || rank == Rank.Ace
      case (Ace(_, ofSuite), Card(rank, suit)) => return ofSuite == suit || currentCard.rank == rank
      case (Seven(_), _) => ???
      case (_, _) => false
    }
  }
}

final case class Normal(currentCard: Card) extends GameState(currentCard)
final case class Ace(currentCard: Card, ofSuite: Suit) extends GameState(currentCard)
final case class Seven(currentCard: Card) extends GameState(currentCard)
final case class Seven2(currentCard: Card) extends GameState(currentCard)
final case class Seven3(currentCard: Card) extends GameState(currentCard)
final case class Ended() extends GameState(null)
