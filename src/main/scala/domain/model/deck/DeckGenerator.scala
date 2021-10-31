package nohponex.agonia.domain.model.deck

import nohponex.agonia.domain.model.deck.{Deck, Stack}
import nohponex.agonia.domain.model.cards.Card

import scala.util.Random

trait DeckGenerator {
  def DeckGenerator(): List[Card]
}

object NewShuflledStackFromDeck extends DeckGenerator{
  def DeckGenerator(): List[Card] = {
    return Random.shuffle(Deck.get())
  }
}

object FixedDeck extends DeckGenerator{
  def DeckGenerator(): List[Card] = {
    return Deck.get()
  }
}

class InjectedDeck(s: List[Card]) extends DeckGenerator{
  def DeckGenerator(): List[Card] = {
    return s
  }
}
