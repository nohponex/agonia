package nohponex.agonia.fp.deck

import nohponex.agonia.fp.deck.{Deck, Stack}

trait DeckGenerator {
  def DeckGenerator(): Stack
}

object NewShuflledStackFromDeck extends DeckGenerator{
  def DeckGenerator(): Stack = {
    return Stack(Deck.get()).shuffle()
  }
}

object FixedDeck extends DeckGenerator{
  def DeckGenerator(): Stack = {
    return Stack(Deck.get())
  }
}
