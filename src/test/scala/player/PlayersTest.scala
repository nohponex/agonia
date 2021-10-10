package player

class PlayersTest extends org.scalatest.funsuite.AnyFunSuite {
  test("Given a game When Player1 Then next SHOULD be Player2") {
    val p = Players(Player.Player1, 2)
    val next = p.Next().Current()
    assert(Player.Player2 == next)
  }

  test("Given a game of two after When Player2 Then next SHOULD be Player1") {
    val p = Players(Player.Player2, 2)
    val next = p.Next().Current()
    assert(Player.Player1 == next)
  }
}
