package nohponex.agonia.es.events

import nohponex.agonia.fp.cards.{Card, Suit}
import nohponex.agonia.fp.player.Player

sealed class Event
trait PlayerActionEvent extends Event

final case class GameStarted(numberOfPlayers: Int) extends Event()

case class PlayerPlayedCard(player: Player, card: Card) extends PlayerActionEvent()
final case class PlayerPlayedCardAce(player: Player, card: Card, ofSuit: Suit) extends PlayerActionEvent()

final case class PlayerDrew(player: Player) extends PlayerActionEvent()
final case class PlayerDrewCard(player: Player, card: Card) extends Event()

final case class PlayerFolded(player: Player) extends PlayerActionEvent()

final case class PlayerEndedTurn(player: Player) extends Event()
