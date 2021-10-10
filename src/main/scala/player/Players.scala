package player

import player.Player.{Player, Player1, Player2}

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
