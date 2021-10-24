package nohponex.agonia.fp.deck

import nohponex.agonia.fp.cards.Card

case class StackPair(private val stack: CardStack, private val usedStack: Stack) {
  def peek(): Card = {
    usedStack.peek()
  }
  def stackLength(): Int = {
    this.stack.length()
  }
  
  def take1(): (StackPair, Card) = {
    val (newStack, newUsedStack) = (stack, usedStack) match {
      case (EmptyStack(), Stack(c)) => {
        val (taken1, card) = usedStack.asInstanceOf[Stack].take1()
        (taken1.asInstanceOf[Stack].shuffle(), Stack(List(card)))
      }
      case _ => (stack, usedStack)
    }
    val (s, c) = newStack.asInstanceOf[Stack].take1()

    (this.copy(stack = s, usedStack = newUsedStack), c)
  }

  def play(c: Card): StackPair = {
    this.copy(usedStack = this.usedStack.push(c))
  }
}
