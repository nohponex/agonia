package game

import deck.{CardStack, EmptyStack, NewShuflledStackFromDeck, Stack}
import player.Players
import player.Player

class Game {
  def Init(): Unit = {
    var players = Players(Player.Player1, 2)

    var playerStacks = players.All().foldLeft(Map.empty[player.Player.Player, deck.CardStack])((a, b) => a + (b -> deck.EmptyStack()))

    var usedStack: CardStack = EmptyStack()
    var stack: CardStack = NewShuflledStackFromDeck()

    for (p <- players.All()) {
      val (s1, cards) = stack.asInstanceOf[deck.Stack].take(7)
      stack = s1

      playerStacks = playerStacks + (p->Stack(cards))
    }
    {
      val (s1, card) = stack.asInstanceOf[deck.Stack].take1()
      stack = s1
      usedStack = usedStack.push(card)
    }

    println(stack)
    println(usedStack)
    println(playerStacks)
  }
}
