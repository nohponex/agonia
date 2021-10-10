package game

import deck.{CardStack, EmptyStack, NewShuflledStackFromDeck, Stack}
import player.Players
import player.Player
import Agonia.cards.{Card, Rank}
import game.*
import scala.io.StdIn.readLine

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

    val (s1, card) = stack.asInstanceOf[deck.Stack].take1()
    stack = s1
    usedStack = usedStack.push(card)

    //init state
    var state: GameState = card match {
      case Card(Rank.Seven, _) => Seven(card)
      case Card(Rank.Eight, _) => Eight(card)
      case Card(Rank.Nine, _) => Nine(card)
      //Rank.Ace should be treated as a normal card on beggining of round
      case _ => Normal(card)
    }

    println(stack)
    println(usedStack)
    println(playerStacks)

    play(state, players, playerStacks)
    //move card to proper stacks
    //update state
    //
    players = state.played(players)
    play(state, players, playerStacks)

    players = state.played(players)
    play(state, players, playerStacks)

    players = state.played(players)
    play(state, players, playerStacks)
  }

  //need to pass choise of suit back
  def play(state: GameState, players: player.Players, playerStacks: Map[Player.Player, CardStack]): Card = {
    println(s"Turn for ${players.Current()} to play!")
    println("Choose between:" )
    for (card, index) <- playerStacks(players.Current()).asInstanceOf[Stack].c.zipWithIndex do {
      println(s"${index}) ${card}")
    }

    val card = playedCard(playerStacks(players.Current()).asInstanceOf[Stack], state)
    println(card)
    card
  }

  def playedCard(stack: Stack, state: GameState): Card = {
    while(true) {
      val read = readLine()
      val asInt = read.toInt

      if asInt >= 0 && asInt < stack.length() then
        val played = stack.c(asInt)
        if state.isAllowed(played) then
          return played
        else
          println(s"$played is not allowed now")
      else
        println("out of index")
    }
    return stack.c(0)
  }
}
