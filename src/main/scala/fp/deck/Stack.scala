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

  def push(cards: List[Card]): Stack = this match {
    case EmptyStack() => Stack(cards)
    case Stack(x) => Stack(x.prependedAll(cards))
  }
  def append(cards: List[Card]): Stack = this match {
    case EmptyStack() => Stack(cards)
    case Stack(x) => Stack(x.appendedAll(cards))
  }
}

final case class EmptyStack() extends CardStack {}
final case class Stack(cards: List[Card]) extends CardStack {
  def remove(card: Card): CardStack = {
      cards.filterNot(_ == card) match {
        case Nil => EmptyStack()
        case s: _ => Stack(s)
      }
  }

  def shuffle(): Stack = {
    Stack(Random.shuffle(cards))
  }

  def peek(): Card = {
    cards(0)
  }

  def take1(): (CardStack, Card) = {
    val res = take(1)
    (res._1, res._2(0))
  }

  def take(n: Int): (CardStack, List[Card]) = {
    if n > this.length() then
      throw new RuntimeException("Cannot take more fp.cards than are in fp.deck")

    if n == this.length() then
      return (EmptyStack(), cards)

    val (s1, s2) = cards.splitAt(n)

    (Stack(s2), s1)
  }
}
