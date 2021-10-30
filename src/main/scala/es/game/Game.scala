package nohponex.agonia.es.game

import fp.deck.FlexibleDeck
import nohponex.agonia.es
import nohponex.agonia.fp.gamestate.*
import nohponex.agonia.es.events.*
import nohponex.agonia.fp.player.{Player, Players}
import nohponex.agonia.fp.cards.{Card, Rank, Suit}
import nohponex.agonia.fp.deck.{CardStack, DeckGenerator, EmptyStack, NewShuflledStackFromDeck, Stack}

import scala.collection.LinearSeq

//default implicit
given deckGenerator: DeckGenerator = NewShuflledStackFromDeck

object Game {
  def NewGame(numberOfPlayer: Int)(using deckGenerator: DeckGenerator): Game = {
    var cards = deckGenerator.DeckGenerator()
        
    Game(
      players = null,
      gameState = null,
      playerStacks = null,
      deck = FlexibleDeck.NewFlexibleDeck(cards),
    ).emit(GameStarted(numberOfPlayer))
  }
}

case class Game(
     players: Players,
     gameState: GameState,
     private val deck: FlexibleDeck,
     private val playerStacks: Map[Player, CardStack],
     private val events: LinearSeq[Event] = LinearSeq(),
     private val currentPlayerDrewCard: Boolean = false,
) {
  def CanFold(): Boolean = {
    currentPlayerDrewCard
  }

  def CanDraw(): Boolean = {
    true
  }

  def peek(): Card = {
    deck.currentCard.get
  }

  def playerStack(p: Player): CardStack = {
    assert(p == players.Current())
    playerStacks(p)
  }

  def play(event: PlayerActionEvent): Game = {
    emit(event)
  }

  private def emit(event: Event): Game =
    this.copy(events = events.appended(event)).apply(event)

  private def emit(events: LinearSeq[Event]): Game =
    events.foldLeft(this.copy(events = events.appendedAll(events))) {(g, e) => g.apply(e)}


  def apply(event: Event): Game = event match {
    case GameStarted(numberOfPlayer) => {

      val players = Players(Player.Player1, numberOfPlayer)

      var playerStacks = players.All().foldLeft(Map.empty[Player, CardStack])((a, p) => a + (p -> EmptyStack()))

      var eventsToEmit: List[Event] = Nil

      var newDeck = deck 
      for (p <- players.All()) {
        val (d1, cards) = newDeck.take(7)
        newDeck = d1
        eventsToEmit = eventsToEmit.appended(DrewCards(p, cards))
      }
      val (_, initialCard) = newDeck.take(1)
      
      eventsToEmit = eventsToEmit.appended(DrewCards(Player.Player1, initialCard))
      eventsToEmit = eventsToEmit.appended(PlayerPlayedCard(Player.Player1, initialCard(0)))

      this.copy(
        players = players,
        playerStacks = playerStacks,
        gameState = gameStateFromInitialCard(initialCard(0)),
      ).emit(eventsToEmit)
    }
    case PlayerPlayedCard(p, c) => {
      assert(this.players.Current() == p)

      if c.rank == Rank.Ace then
        assert(gameState.isInstanceOf[Ace])

      c.rank match {
        case Rank.Seven => this.copy(
          gameState = gameState match {
            case a: Base7 => a.Escalate(c)
            case _ => Seven(c)
          },
          players = players.Next(),
          deck = deck.play(c),
          playerStacks = playerStacks + (p -> playerStacks(p).asInstanceOf[Stack].remove(c)),
          currentPlayerDrewCard = false
        ).emit(PlayerEndedTurn(p))
        case Rank.Eight => this.copy(
          gameState = Normal(c),
          deck = deck.play(c),
          playerStacks = playerStacks + (p -> playerStacks(p).asInstanceOf[Stack].remove(c)),
          currentPlayerDrewCard = false
        ).emit(PlayerEndedTurn(p))
        case Rank.Nine => this.copy(
          gameState = Normal(c),
          players = players.Next().Next(),
          deck = deck.play(c),
          playerStacks = playerStacks + (p -> playerStacks(p).asInstanceOf[Stack].remove(c)),
          currentPlayerDrewCard = false
        ).emit(PlayerEndedTurn(p))

        case _ => this.copy(
          gameState = Normal(c),
          players = players.Next(),
          deck = deck.play(c),
          playerStacks = playerStacks + (p -> playerStacks(p).asInstanceOf[Stack].remove(c))
        ).emit(PlayerEndedTurn(p))
      }
    }
    case PlayerPlayedCardAce(p, c, ofSuite) => {
      assert(this.players.Current() == p)
      assert(c.rank == Rank.Ace)
      assert(!gameState.isInstanceOf[Ace])

      this.copy(
        players = players.Next(),
        gameState = Ace(currentCard = c, ofSuite),
        deck = deck.play(c),
        playerStacks = playerStacks + (p -> playerStacks(p).asInstanceOf[Stack].remove(c))
      ).emit(PlayerEndedTurn(p))
    }
    case PlayerEndedTurn(p) => {
      if this.playerStacks(p).length() == 0 then {
        return this.copy(
          gameState = Ended()
        )
      }

      this.copy(
        currentPlayerDrewCard = false,
      )
    }
    case PlayerDrew(p) => {
      assert(this.players.Current() == p)

      gameState match {
        case a: Base7 => {
          val (newStackPair, cards) = deck.take(a.ToDraw())

          this.copy(
            gameState = Normal(gameState.CurrentCard),
            currentPlayerDrewCard = false
          ).emit(DrewCards(p, cards))
        }
        case _ => {
          val (_, cards) = this.deck.take(1)

          this.copy(
            currentPlayerDrewCard = true
          ).emit(DrewCards(p, cards))
        }
      }
    }
    case DrewCards(p, cards) => {
      this.copy(
        deck = deck.remove(cards),
        playerStacks = playerStacks + (p -> playerStacks(p).append(cards))
      )
    }
    case PlayerFolded(p) => {
      assert(currentPlayerDrewCard)

      this.copy(
        players = this.gameState.played(this.players)
      ).emit(PlayerEndedTurn(p))
    }
    case _ => ???
  }

  def ObservableEvents(): LinearSeq[Event] = events.filter(_.isInstanceOf[PlayerActionEvent])
}

def gameStateFromInitialCard(card: Card): GameState = (card.rank, card.suit) match {
  case (Rank.Seven, _) => Seven(card)
  case _ => Normal(card)
}
