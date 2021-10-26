package nohponex.agonia.fp.player

import nohponex.agonia.fp.player.Player

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

  def All(): List[Player] = {
    List(Player.Player1, Player.Player2)
  }
}
