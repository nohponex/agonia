package nohponex.agonia.ai

import nohponex.agonia.es.events.{PlayerActionEvent, PlayerDrew, PlayerFolded, PlayerPlayedCard}
import nohponex.agonia.es.game.Game
import nohponex.agonia.fp.deck.Stack
import nohponex.agonia.fp.gamestate.GameState
import nohponex.agonia.fp.player.Player

object RobotPlayer {
  def play(
      g : Game,
     player: Player,
     stack: Stack,
     state: GameState,
  ): PlayerActionEvent = {
    stack.cards.filter(state.isAllowed(_)) match {
      case s :: rest => return PlayerPlayedCard(player, s)
      case Nil =>
    }

    if g.CanFold() then
      return PlayerFolded(player)

    PlayerDrew(player)
  }
}
