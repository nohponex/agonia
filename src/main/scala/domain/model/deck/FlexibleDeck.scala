package nohponex.agonia.domain.model.deck

import nohponex.agonia.domain.model.cards.Card

import scala.util.Random

object FlexibleDeck {
  def NewFlexibleDeck(deck: List[Card]): FlexibleDeck = FlexibleDeck(
    currentCard = None,
    played = Nil,
    available = deck,
  )
}

case class FlexibleDeck(currentCard: Option[Card], played: List[Card], available: List[Card]) {
  def play(card: Card): FlexibleDeck = this.copy(
    currentCard = Some(card),
    played = currentCard match {
      case Some(_) => played.appended(currentCard.get)
      case _ => played
    }
  )

  def take(n: Int): (FlexibleDeck, List[Card]) = {
    if n <= available.length then {
      val (a1, a2) = available.splitAt(n)

      return (this.copy(available = a2), a1)
    }

    val leftOver = n - available.length

    val (u1, u2) = Random.shuffle(played).splitAt(leftOver)

    (this.copy(played = Nil, available = u2), available ::: u1)
  }

  def remove(cards: List[Card]): FlexibleDeck = {
    assert(!cards.contains(currentCard))
    val nowAvailable = available.filterNot(cards.contains(_))
    val nowPlayed = played.filterNot(cards.contains(_))

    assert(available.length + played.length == nowAvailable.length + cards.length + nowPlayed.length)
    this.copy(
      available = nowAvailable,
      played = nowPlayed,
    )
  }
}
