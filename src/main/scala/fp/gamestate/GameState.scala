package nohponex.agonia.fp.gamestate

import nohponex.agonia.fp.cards.{Card, Rank, Suit}
import nohponex.agonia.fp.player.Players

sealed trait Base7 extends GameState {
  def ToDraw(): Int

  def Escalate(): Base7
}

sealed trait GameState (currentCard: Card) {
  def CurrentCard: Card = currentCard
  def played(p: Players): Players = {
    this match {
      case _ => p.Next()
    }
  }

  def isAllowed(c: Card): Boolean = {
    (this, c) match {
      case (Normal(_), Card(rank, suit)) => return currentCard.suit == suit || currentCard.rank == rank || rank == Rank.Ace
      case (Ace(_, ofSuite), Card(rank, suit)) => return ofSuite == suit || currentCard.rank == rank
      case (Seven(_), _) | (Seven2(_), _) | (Seven3(_), _) => return c.rank == Rank.Seven
      case (_, _) => false
    }
  }
}

final case class Normal(currentCard: Card) extends GameState(currentCard)
final case class Ace(currentCard: Card, ofSuite: Suit) extends GameState(currentCard)
final case class Seven(currentCard: Card) extends GameState(currentCard) with Base7 {
  override def ToDraw(): Int = 2
  override def Escalate(): Base7 = Seven2(currentCard)
}
final case class Seven2(currentCard: Card) extends GameState(currentCard) with Base7 {
  override def ToDraw(): Int = 4
  override def Escalate(): Base7 = Seven2(currentCard)
}
final case class Seven3(currentCard: Card) extends GameState(currentCard) with Base7{
  override def ToDraw(): Int = 6
  override def Escalate(): Base7 = Seven4(currentCard)
}
final case class Seven4(currentCard: Card) extends GameState(currentCard) with Base7{
  override def ToDraw(): Int = 8
  override def Escalate(): Base7 = ???
}
final case class Ended() extends GameState(null)
