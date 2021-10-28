package nohponex.agonia.fp.deck

import nohponex.agonia.fp.cards.Card

case class StackPair(private val stack: CardStack, private val usedStack: CardStack) {
  def peek(): Card = {
    usedStack.asInstanceOf[Stack].peek()
  }
  def stackLength(): Int = {
    this.stack.length()
  }

  def take(n: Int): (StackPair, List[Card]) = {
    var cards: List[Card] = Nil

    var pair = this
    for (i <- 0 until n) {
      val (pp, c) = pair.take1()
      cards = cards.appended(c)
      pair = pp
    }

    return (pair, cards)
  }

  def take1(): (StackPair, Card) = {
    val (newStack, newUsedStack) = (stack, usedStack) match {
      case (EmptyStack(), Stack(c)) => {
        usedStack.asInstanceOf[Stack].take1() match {
          case (s: Stack, c: Card) => (s.shuffle(), Stack(List(c)))
          case (s: CardStack, c: Card) => (s, Stack(List(c)))
        }
      }
      case _ => (stack, usedStack)
    }
    val (s, c) = newStack.asInstanceOf[Stack].take1()

    (this.copy(stack = s, usedStack = newUsedStack), c)
  }

  def play(c: Card): StackPair = {
    this.copy(usedStack = this.usedStack.push(c))
  }

  def remove(cards: List[Card]): StackPair = {
    val s = cards.foldLeft(stack) ((s, c) => s match {
      case EmptyStack() => s
      case s: Stack => s.remove(c)
    })
    assert(stack.length() == s.length() + cards.length)
    this.copy(stack = s)
  }
}
