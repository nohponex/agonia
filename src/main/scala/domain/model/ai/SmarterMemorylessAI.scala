package nohponex.agonia.domain.model.ai

import nohponex.agonia.domain.model.Game
import nohponex.agonia.domain.model.cards.{Card, Rank, Suit}
import nohponex.agonia.domain.model.deck.Stack
import nohponex.agonia.domain.model.events.*
import nohponex.agonia.domain.model.gamestate.{Ace, GameState}
import nohponex.agonia.domain.model.player.Player

/**
 * This implementation will prefer 8 and 9 over other cards, and A less than all others
 */
object SmarterMemorylessAI {
  def play(
     g : Game,
     player: Player,
     stack: Stack,
     state: GameState,
  ): PlayerActionEvent = {
    stack.cards.sortBy(
      c => c.rank match {
        case Rank.Ace => 1
        case Rank.Eight => -2
        case Rank.Nine => -1
        case _ => 0
      }
    ).filter(state.isAllowed(_)) match {
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
