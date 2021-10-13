package nohponex.agonia.fp.player

import nohponex.agonia.fp.player.Player

case class Players(private var current: Player, private var numberOfPlayer: Int) {
  def Next(): Players = {
    if current == Player.Player1 then {
      return this.copy(current = Player.Player2)
    }

    this.copy(current = Player.Player1)
  }

  def Current(): Player = current

  def All(): List[Player] = {
    List(Player.Player1, Player.Player2)
  }
}
