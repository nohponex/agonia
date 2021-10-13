package nohponex.agonia.fp.deck

import nohponex.agonia.fp.cards.Card

case class StackPair(private val stack: CardStack, private val usedStack: CardStack = EmptyStack()) {
  def take1(): (StackPair, Card) = {
    take(1) match
      case (a, b) => (a, b(0))
  }

  def take(n: Int): (StackPair, List[Card]) = {
    //todo take(n-length) first
    val res = stack match {
      case Stack(c) => (stack, usedStack)
      case EmptyStack() => (usedStack.asInstanceOf[Stack].shuffle(), EmptyStack())
    }
    val (s, c) = res._1.asInstanceOf[Stack].take(n)

    (this.copy(stack = s, usedStack = res._2), c)
  }

  def push(c: Card): StackPair = {
    this.copy(stack = this.stack, usedStack = this.usedStack.push(c))
  }
}
