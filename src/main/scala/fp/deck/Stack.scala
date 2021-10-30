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

  /**
   * @deprecated
   */
  def peek(): Card = {
    cards(0)
  }
}
