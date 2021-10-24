package nohponex.agonia.fp.game

import nohponex.agonia.fp.gamestate.{Eight, GameState, Nine, Normal, Seven}
import nohponex.agonia.fp.deck.{CardStack, EmptyStack, NewShuflledStackFromDeck, Stack}
import nohponex.agonia.fp.player.Players
import nohponex.agonia.fp.player.Player
import nohponex.agonia.fp.cards.{Card, Rank}
import nohponex.agonia.fp.game.*

import scala.io.StdIn.readLine

class Game {
  def Init(): Unit = {
    var players = Players(Player.Player1, 2)

    var playerStacks = players.All().foldLeft(Map.empty[Player, CardStack])((a, b) => a + (b -> EmptyStack()))

    //those two need to handled by same container, since an after take() if it's empty we need to re-shuffle usedStack
    var usedStack: CardStack = EmptyStack()
    var stack: CardStack = NewShuflledStackFromDeck.DeckGenerator()

    for (p <- players.All()) {
      val (s1, cards) = stack.asInstanceOf[Stack].take(7)
      stack = s1

      playerStacks = playerStacks + (p -> Stack(cards))
    }

    val (s1, card) = stack.asInstanceOf[Stack].take1()
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
}

//need to pass choise of suit back
def play(state: GameState, players: Players, playerStacks: Map[Player, CardStack]): Card = {
  println(s"Turn for ${players.Current()} to play!")
  println("Choose between:" )
  for (card, index) <- playerStacks(players.Current()).asInstanceOf[Stack].c.zipWithIndex do {
    println(s"- ${index}) ${card}")
  }
  println("- or you can draw")
  println("- or you can fold")

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

