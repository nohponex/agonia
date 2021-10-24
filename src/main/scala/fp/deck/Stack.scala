package nohponex.agonia.fp.deck

import nohponex.agonia.fp.cards.Card

import scala.util.Random

sealed trait CardStack {
  //possibly not need for EmptyStack to have length at all
  def length(): Int = this match {
    case EmptyStack() => 0
    case Stack(x) => x.length
  }

  def push(card: Card): Stack = this match {
    case EmptyStack() => Stack(List(card))
    case Stack(x) => Stack(x.prepended(card))
  }
}
final case class EmptyStack() extends CardStack {
}
final case class Stack(c: List[Card]) extends CardStack {
  def shuffle(): Stack = {
    Stack(Random.shuffle(c))
  }

  def peek(): Card = {
    c(0)
  }

  def take1(): (CardStack, Card) = {
    val res = take(1)
    (res._1, res._2(0))
  }

  def take(n: Int): (CardStack, List[Card]) = {
    if n > this.length() then
      throw new RuntimeException("Cannot take more fp.cards than are in fp.deck")

    if n == this.length() then
      return (EmptyStack(), c)

    val (s1, s2) = c.splitAt(n)

    (Stack(s2), s1)
  }
}
