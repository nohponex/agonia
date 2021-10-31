package nohponex.agonia.ai

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
    val allowedCards = stack.cards.filter(state.isAllowed(_))
    //todo prefer Ace last
    //todo prefer 8 if has same Suit
    match {
      case s :: rest => return (s, state) match {
        case (Card(Rank.Ace, _), ss) if !ss.isInstanceOf[Ace] => PlayerPlayedCardAce(player, s, preferedSuit(stack.cards))
        case _ => PlayerPlayedCard(player, s)
      }
      case Nil =>
    }

    if g.CanFold() then
      return PlayerFolded(player)

    PlayerDrew(player)
  }

  def preferedSuit(cards: List[Card]): Suit = {
    if cards.isEmpty then
      return Suit.Hearts

    cards.groupBy(_.suit).view.mapValues(_.length).toSeq.sortWith(_._2 > _._2).head._1
  }
}
