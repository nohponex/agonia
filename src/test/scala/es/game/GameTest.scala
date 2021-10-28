package nohponex.agonia.es.game

import nohponex.agonia.fp.cards.{Card, Rank, Suit}
import nohponex.agonia.fp.deck.{DeckGenerator, FixedDeck, InjectedDeck, NewShuflledStackFromDeck, Stack}
import nohponex.agonia.fp.player.{Player, Players}
import nohponex.agonia.fp.gamestate
import nohponex.agonia.fp.gamestate.{Normal, Seven}

given dockGenerator:DeckGenerator = FixedDeck

class GameTest extends org.scalatest.funsuite.AnyFunSuite {
  test("startAGameOf2 should start a game where both players have 7 cards and one is open") {
    val g = startAGameOf2()

    assert(g.playerStacks(Player.Player1).length() == 7)
    assert(g.playerStacks(Player.Player2).length() == 7)
    assert(g.stackPair.peek() != null)
    assert(g.stackPair.stackLength() == 52-1-7-7)
  }

  test("gameStateFromInitialCard is Seven") {
    assert(gameStateFromInitialCard(Card(Rank.Seven, Suit.Spades)).isInstanceOf[Seven])
  }

  test("gameStateFromInitialCard is Normal") {
    assert(gameStateFromInitialCard(Card(Rank.Ace, Suit.Spades)).isInstanceOf[Normal])
    assert(gameStateFromInitialCard(Card(Rank.Two, Suit.Spades)).isInstanceOf[Normal])
  }

  test("given Initial Card is Something and GameState is Normal then it should be Player2 turn to play") {
    val g = startAGameOf2()

    require(g.gameState.isInstanceOf[Normal])
    assert(g.players.Current() == Player.Player2)
  }

  test("given Initial Card is Eight then should be Player1 turn to play") {
    //todo using factories might be an exaggeration since we can create any state we want
    given dockGenerator:DeckGenerator = InjectedDeck(Stack(List(
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),

      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),

      Card(Rank.Eight, Suit.Hearts),
    )))
    val g = startAGameOf2()

    require(g.stackPair.peek().rank == Rank.Eight)
    assert(g.players.Current() == Player.Player1)
  }

  test("given Initial Card is Nine and then it should be Player1 turn to play") {
    given dockGenerator:DeckGenerator = InjectedDeck(Stack(List(
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),

      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),
      Card(Rank.Ace, Suit.Spades),

      Card(Rank.Nine, Suit.Hearts),
    )))
    val g = startAGameOf2()

    require(g.stackPair.peek().rank == Rank.Nine)
    assert(g.players.Current() == Player.Player1)
  }

  test("given Player1 Played Ace") {

  }
  
  test("given GameStarted") {
    val g = Game.NewGame(2)
    assert(g.stackPair.stackLength() == 52-7-7-1)
    assert(g.playerStacks(Player.Player1).length() == 7)
    assert(g.playerStacks(Player.Player2).length() == 7)

    g.stackPair.peek()
  }
}
