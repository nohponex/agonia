package nohponex.agonia.domain.model.events

import nohponex.agonia.domain.model.cards.{Card, Suit}
import nohponex.agonia.domain.model.player.Player

sealed class Event
sealed class PlayerActionEvent extends Event

final case class GameStarted(numberOfPlayers: Int) extends Event()

case class PlayerPlayedCard(player: Player, card: Card) extends PlayerActionEvent()
final case class PlayerPlayedCardAce(player: Player, card: Card, ofSuit: Suit) extends PlayerActionEvent()

final case class PlayerDrew(player: Player) extends PlayerActionEvent()
final case class DrewCards(player: Player, cards: List[Card]) extends Event()

final case class PlayerFolded(player: Player) extends PlayerActionEvent()

final case class PlayerEndedTurn(player: Player) extends Event()
