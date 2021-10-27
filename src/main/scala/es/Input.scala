package nohponex.agonia.es

import nohponex.agonia.es.events.{Event, PlayerActionEvent, PlayerDrew, PlayerFolded, PlayerPlayedCard, PlayerPlayedCardAce}
import nohponex.agonia.es.game.Game
import nohponex.agonia.fp.cards.{Rank, Suit}
import nohponex.agonia.fp.deck.Stack
import nohponex.agonia.fp.gamestate.{Ace, GameState}
import nohponex.agonia.fp.player.Player

import io.AnsiColor.*
import scala.io.StdIn.readLine

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
            println(s"$played is not allowed now")
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
