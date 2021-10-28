package nohponex.agonia.es.game

import nohponex.agonia.fp.cards.{Card, Rank, Suit}
import nohponex.agonia.fp.deck.{DeckGenerator, FixedDeck, InjectedDeck, NewShuflledStackFromDeck, Stack}
import nohponex.agonia.fp.player.{Player, Players}
import nohponex.agonia.fp.gamestate
import nohponex.agonia.fp.gamestate.{Normal, Seven}

given dockGenerator:DeckGenerator = FixedDeck

class GameTest extends org.scalatest.funsuite.AnyFunSuite {

  test("gameStateFromInitialCard is Seven") {
    assert(gameStateFromInitialCard(Card(Rank.Seven, Suit.Spades)).isInstanceOf[Seven])
  }

  test("gameStateFromInitialCard is Normal") {
    assert(gameStateFromInitialCard(Card(Rank.Ace, Suit.Spades)).isInstanceOf[Normal])
    assert(gameStateFromInitialCard(Card(Rank.Two, Suit.Spades)).isInstanceOf[Normal])
  }

  test("given Initial Card is Something and GameState is Normal then it should be Player2 turn to play") {
    val g = Game.NewGame(2)

    require(g.gameState.isInstanceOf[Normal])
    assert(g.players.Current() == Player.Player2)
  }

  test("given Initial Card is Eight then should be Player1 turn to play") {
    //todo using factories might be an exaggeration since we can create any state we want
    given dockGenerator:DeckGenerator = InjectedDeck(Stack(List(
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.King, Suit.Spades),
      Card(Rank.Queen, Suit.Spades),
      Card(Rank.Jack, Suit.Spades),
      Card(Rank.Ten, Suit.Spades),
      Card(Rank.Nine, Suit.Spades),
      Card(Rank.Eight, Suit.Spades),

      Card(Rank.Seven, Suit.Spades),
      Card(Rank.Six, Suit.Spades),
      Card(Rank.Five, Suit.Spades),
      Card(Rank.Four, Suit.Spades),
      Card(Rank.Three, Suit.Spades),
      Card(Rank.Two, Suit.Spades),
      Card(Rank.Ace, Suit.Hearts),

      Card(Rank.Eight, Suit.Hearts),
    )))
    val g = Game.NewGame(2)

    require(g.peek().rank == Rank.Eight)
    assert(g.players.Current() == Player.Player1)
  }

  test("given Initial Card is Nine and then it should be Player1 turn to play") {
    given dockGenerator:DeckGenerator = InjectedDeck(Stack(List(
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.King, Suit.Spades),
      Card(Rank.Queen, Suit.Spades),
      Card(Rank.Jack, Suit.Spades),
      Card(Rank.Ten, Suit.Spades),
      Card(Rank.Nine, Suit.Spades),
      Card(Rank.Eight, Suit.Spades),

      Card(Rank.Seven, Suit.Spades),
      Card(Rank.Six, Suit.Spades),
      Card(Rank.Five, Suit.Spades),
      Card(Rank.Four, Suit.Spades),
      Card(Rank.Three, Suit.Spades),
      Card(Rank.Two, Suit.Spades),
      Card(Rank.Ace, Suit.Hearts),

      Card(Rank.Nine, Suit.Hearts),
    )))
    val g = Game.NewGame(2)

    require(g.peek().rank == Rank.Nine)
    assert(g.players.Current() == Player.Player1)
  }

  test("given Player1 Played Ace") {

  }
}
