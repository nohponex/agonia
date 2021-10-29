package nohponex.agonia.es

import nohponex.agonia.es.events.{Event, PlayerActionEvent, PlayerDrew, PlayerFolded, PlayerPlayedCard, PlayerPlayedCardAce}
import nohponex.agonia.es.game.Game
import nohponex.agonia.fp.cards.{Rank, Suit, Card}
import nohponex.agonia.fp.deck.Stack
import nohponex.agonia.fp.gamestate.{Ace, Base7, GameState}
import nohponex.agonia.fp.player.Player

import io.AnsiColor.*
import scala.io.StdIn.readLine

def play(
  g : Game,
  player: Player,
  stack: Stack,
  state: GameState,
): PlayerActionEvent = {
  if g.CanFold() then {
    println(s"You can fold, type \"${UNDERLINED}f${RESET}old\"")
  }
  if state.isInstanceOf[Base7] then {
    val ToCount = state.asInstanceOf[Base7].ToDraw()
    println(s"You have either play 7 or to draw (${ToCount}), type \"${UNDERLINED}d${RESET}raw\"")
  } else if g.CanDraw() then {
    println(s"You can draw, type \"${UNDERLINED}d${RESET}raw\"")
  }

  println("Choose between:")
  for (card
  , index
  ) <- stack.cards.zipWithIndex
  do {
    card.suit match {
      case Suit.Spades | Suit.Clubs => println(s"- ${UNDERLINED}${index}${RESET}) ${formatedCard(card)}")
      case Suit.Diamonds | Suit.Hearts => println(s"- ${UNDERLINED}${index}${RESET}) ${formatedCard(card)}")
    }

  }
  playedCard(
    g,
    g.players.Current(),
    stack,
    g.gameState
  )
}

def playedCard(
    g : Game,
    player: Player,
    stack: Stack,
    state: GameState,
): PlayerActionEvent = {
  while(true) {
    val read = readLine()

    if read.toLowerCase().startsWith("d") && g.CanDraw() then {
      return PlayerDrew(player)
    }
    if read.toLowerCase().startsWith("f") && g.CanFold() then {
      return PlayerFolded(player)
    }

    read.toIntOption match {
      case None => {}
      case Some(asInt) => {
        if asInt >= 0 && asInt < stack.length() then
          val played = stack.cards(asInt)
          if state.isAllowed(played) then
            if played.rank == Rank.Ace && !state.isInstanceOf[Ace] then {
              return PlayerPlayedCardAce(player, played, aceOfSuit())
            }
            return PlayerPlayedCard(player, played)
          else
            println(s"${formatedCard(played)} is not allowed now")
        else
          println("out of index")
      }
    }
  }

  //nop
  return PlayerFolded(player)
}

def aceOfSuit(): Suit = {
  while (true) {
    println(
      s"Choose a suit for your ace "
      + s"(${BLACK}${Suit.Clubs}${UNDERLINED}C${RESET}lubs${RESET},"
      + s"${RED}${Suit.Diamonds}${UNDERLINED}D${RESET}iamonds${RESET}, "
      + s"${RED}${Suit.Hearts}${UNDERLINED}H${RESET}earts${RESET}, "
      + s"${BLACK}${Suit.Spades}${UNDERLINED}S${RESET}pades${RESET}):"
    )
    val c = readLine().toLowerCase()

    if c.startsWith("c") then return Suit.Clubs
    if c.startsWith("d") then return Suit.Diamonds
    if c.startsWith("h") then return Suit.Hearts
    if c.startsWith("s") then return Suit.Spades
  }
  Suit.Hearts
}

def formatedSuit(suit: Suit): String = suit match {
  case Suit.Spades | Suit.Clubs => s"${BLACK}${suit}${RESET}"
  case Suit.Diamonds | Suit.Hearts => s"-${RED}${suit}${RESET}"
}

def formatedCard(card: Card): String = card.suit match {
  case Suit.Spades | Suit.Clubs => s"${BLACK}${card}${RESET}"
  case Suit.Diamonds | Suit.Hearts => s"-${RED}${card}${RESET}"
}
