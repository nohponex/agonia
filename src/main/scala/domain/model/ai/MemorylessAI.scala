package nohponex.agonia.domain.model.ai

import nohponex.agonia.domain.model.Game
import nohponex.agonia.domain.model.events.{PlayerActionEvent, PlayerDrew, PlayerFolded, PlayerPlayedCard, PlayerPlayedCardAce}
import nohponex.agonia.domain.model.cards.{Card, Rank, Suit}
import nohponex.agonia.domain.model.deck.Stack
import nohponex.agonia.domain.model.gamestate.{Ace, GameState}
import nohponex.agonia.domain.model.player.Player

object MemorylessAI {
  def play(
     g : Game,
     player: Player,
     stack: Stack,
     state: GameState,
  ): PlayerActionEvent = {
    stack.cards.filter(state.isAllowed(_)) match {
      case s::_ => s.rank match {
        case Rank.Ace => state match {
          case _: Ace => PlayerPlayedCard(player, s)
          case _ => PlayerPlayedCardAce(player, s, preferedSuit(stack.cards))
        }
        case _ => PlayerPlayedCard(player, s)
      }
      case Nil => {
        if g.CanFold() then
          return PlayerFolded(player)

        PlayerDrew(player)
      }
    }
  }

  def preferedSuit(cards: List[Card]): Suit = {
    cards.filterNot(_.rank == Rank.Ace) match
      case Nil => Suit.Hearts
      case _ =>
        cards.groupBy(_.suit).view.mapValues(_.length).toSeq.sortWith(_._2 > _._2).head._1
  }
}
