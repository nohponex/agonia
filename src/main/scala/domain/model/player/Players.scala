package nohponex.agonia.domain.model.player

import nohponex.agonia.domain.model.player.Player

case class Players(private var current: Player, private var numberOfPlayer: Int) {
  assert(numberOfPlayer > 0)
  assert(numberOfPlayer <= 4)

  def Next(): Players = {
    if current == Player.Player1 then {
      return this.copy(current = Player.Player2)
    }

    if current == Player.Player2 && numberOfPlayer >= 3 then {
      return this.copy(current = Player.Player3)
    }

    if current == Player.Player3 && numberOfPlayer >= 4 then {
      return this.copy(current = Player.Player4)
    }

    this.copy(current = Player.Player1)
  }

  def Current(): Player = current

  def All(): List[Player] = numberOfPlayer match {
    case 2 => List(Player.Player1, Player.Player2)
    case 3 => List(Player.Player1, Player.Player2, Player.Player3)
    case 4 => List(Player.Player1, Player.Player2, Player.Player3, Player.Player4)
  }
}
