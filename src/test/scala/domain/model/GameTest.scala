package domain.model

import nohponex.agonia.domain.model.events.{PlayerDrew, PlayerPlayedCard, PlayerPlayedCardAce}
import nohponex.agonia.domain.model.cards.{Card, Rank, Suit}
import nohponex.agonia.domain.model.deck.*
import nohponex.agonia.domain.model.{Game, gameStateFromInitialCard}
import nohponex.agonia.domain.model.gamestate.{Normal, Seven}
import nohponex.agonia.domain.model.player.{Player, Players}

given deckGenerator:DeckGenerator = FixedDeck

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
    given deckGenerator:DeckGenerator = InjectedDeck(List(
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
    ))
    val g = Game.NewGame(2)

    require(g.peek().rank == Rank.Eight)
    assert(g.players.Current() == Player.Player1)
  }

  test("given Initial Card is Nine and then it should be Player1 turn to play") {
    given deckGenerator:DeckGenerator = InjectedDeck(List(
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
    ))
    val g = Game.NewGame(2)

    require(g.peek().rank == Rank.Nine)
    assert(g.players.Current() == Player.Player1)
  }

  test("determisitic deck") {
    given deckGenerator:DeckGenerator = InjectedDeck(List(
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

      Card(Rank.Three, Suit.Hearts),
    ))
    val g = Game.NewGame(2)
      .play(PlayerPlayedCard(Player.Player2, Card(Rank.King, Suit.Spades)))
      .play(PlayerDrew(Player.Player1))

    assert(g.players.Current() == Player.Player1)
  }
}
