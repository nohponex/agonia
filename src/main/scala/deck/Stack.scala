package deck

import Agonia.cards.Card

sealed trait CardStack {
  //possibly not need for EmptyStack to have length at all
  def length(): Int = this match {
    case EmptyStack() => 0
    case Stack(x) => x.length
  }
}
final case class EmptyStack() extends CardStack {
  def push(card: Card): Stack = Stack(List(card))
}
final case class Stack(c: List[Card]) extends CardStack {
  def shuffle(): Stack = {
    Stack(c.reverse)
  }

  def push(card: Card): Stack = Stack(c.prepended(card))

  def peek(): Card = {
    c(0)
  }

  def take1(): (CardStack, Card) = {
    val res = take(1)
    (res._1, res._2(0))
  }

  def take(n: Int): (CardStack, List[Card]) = {
    if n > this.length() then
      throw new RuntimeException("Cannot take more cards than are in deck")

    if n == this.length() then
      return (EmptyStack(), c)

    val (s1, s2) = c.splitAt(n)

    (Stack(s2), s1)
  }
}

def NewStackFromDeck(): Stack = {
  val d = Deck.get()

  return Stack(d).shuffle()
}
