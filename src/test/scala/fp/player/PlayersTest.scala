package nohponex.agonia.fp.player

class PlayersTest extends org.scalatest.funsuite.AnyFunSuite {
  test("Given a fp.game When Player1 Then next SHOULD be Player2") {
    val p = Players(Player.Player1, 2)
    val next = p.Next().Current()
    assert(Player.Player2 == next)
  }

  test("Given a fp.game of two after When Player2 Then next SHOULD be Player1") {
    val p = Players(Player.Player2, 2)
    val next = p.Next().Current()
    assert(Player.Player1 == next)
  }

  test("Given a fp.game of three after When Player2 Then next SHOULD be Player3") {
    val p = Players(Player.Player2, 3)
    val next = p.Next().Current()
    assert(Player.Player3 == next)
  }

  test("Given a fp.game of three after When Player3 Then next SHOULD be Player1") {
    val p = Players(Player.Player3, 3)
    val next = p.Next().Current()
    assert(Player.Player1 == next)
  }

  test("Given a fp.game of four after When Player3 Then next SHOULD be Player4") {
    val p = Players(Player.Player3, 4)
    val next = p.Next().Current()
    assert(Player.Player4 == next)
  }
}
