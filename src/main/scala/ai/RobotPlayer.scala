package nohponex.agonia.ai

import nohponex.agonia.es.events.{PlayerActionEvent, PlayerDrew, PlayerFolded, PlayerPlayedCard, PlayerPlayedCardAce}
import nohponex.agonia.es.game.Game
import nohponex.agonia.fp.cards.{Card, Rank, Suit}
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
    val allowedCards = stack.cards.filter(state.isAllowed(_))
    //todo prefer Ace last
    match {
      case s :: rest => return s match {
        case Card(Rank.Ace, _) => PlayerPlayedCardAce(player, s, preferedSuit(stack.cards))
        case _ => PlayerPlayedCard(player, s)
      }
      case Nil =>
    }

    if g.CanFold() then
      return PlayerFolded(player)

    PlayerDrew(player)
  }

  def preferedSuit(cards: List[Card]): Suit = {
    cards.groupBy(_.suit).mapValues(_.length).toSeq.sortWith(_._2 > _._2).head._1
  }
}
