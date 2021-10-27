package nohponex.agonia.es.game

import nohponex.agonia.es
import nohponex.agonia.fp.gamestate.*
import nohponex.agonia.es.events.*
import nohponex.agonia.fp.player.{Player, Players}
import nohponex.agonia.fp.cards.{Card, Rank, Suit}
import nohponex.agonia.fp.deck.{CardStack, EmptyStack, NewShuflledStackFromDeck, Stack, StackPair, DeckGenerator}
import scala.collection.LinearSeq

//default implicit
given dockGenerator: DeckGenerator = NewShuflledStackFromDeck

def startAGameOf(numberOfPlayer: Int)(using dockGenerator: DeckGenerator): Game = {
  val players = Players(Player.Player1, numberOfPlayer)

  var playerStacks = players.All().foldLeft(Map.empty[Player, CardStack])((a, b) => a + (b -> EmptyStack()))
  var usedStack: CardStack = EmptyStack()
  var stack: CardStack = dockGenerator.DeckGenerator()

  for (p <- players.All()) {
    val (s1, cards) = stack.asInstanceOf[Stack].take(7)
    stack = s1
    playerStacks = playerStacks + (p -> Stack (cards) )
  }
  val (s1, card) = stack.asInstanceOf[Stack].take1()
  stack = s1

  Game(
    players = players,
    gameState = gameStateFromInitialCard(card),
    playerStacks = playerStacks,
    stackPair = StackPair(stack, Stack(List(card))),
    LinearSeq(
      GameStarted(numberOfPlayer),
    )
  )
    .play(PlayerPlayedCard(Player.Player1, card))
}

def startAGameOf2()(using dockGenerator: DeckGenerator): Game = {
  startAGameOf(2)
}

case class Game(
     players: Players,
     gameState: GameState,
     playerStacks: Map[Player, CardStack],
     stackPair: StackPair,
     events: LinearSeq[Event] = LinearSeq(),
     currentPlayerDrewCard: Boolean = false,
) {
  def CanFold(): Boolean = {
    currentPlayerDrewCard
  }

  def CanDraw(): Boolean = {
    true
  }

  def play(event: PlayerActionEvent): Game = {
    emit(event)
  }

  private def emit(event: Event): Game = {
    this.copy(events = events.appended(event)).apply(event)
  }

  def apply(event: Event): Game = event match {
    case GameStarted(_) => {
      this
    }
    case PlayerPlayedCard(p, c) => {
      assert(this.players.Current() == p)

      c.rank match {
        case Rank.Seven => this.copy(
          gameState = gameState match {
            case a: Base7 => a.Escalate(c)
            case _ => Seven(c)
          },
          players = players.Next(),
          stackPair = stackPair.play(c),
          playerStacks = playerStacks + (p -> playerStacks(p).asInstanceOf[Stack].remove(c)),
          currentPlayerDrewCard = false
        ).emit(PlayerEndedTurn(p))
        case Rank.Eight => this.copy(
          gameState = Normal(c),
          stackPair = stackPair.play(c),
          playerStacks = playerStacks + (p -> playerStacks(p).asInstanceOf[Stack].remove(c)),
          currentPlayerDrewCard = false
        ).emit(PlayerEndedTurn(p))
        case Rank.Nine => this.copy(
          gameState = Normal(c),
          players = players.Next().Next(),
          stackPair = stackPair.play(c),
          playerStacks = playerStacks + (p -> playerStacks(p).asInstanceOf[Stack].remove(c)),
          currentPlayerDrewCard = false
        ).emit(PlayerEndedTurn(p))

        case _ => this.copy(
          gameState = Normal(c),
          players = players.Next(),
          stackPair = stackPair.play(c),
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
        stackPair = stackPair.play(c),
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
          var cards: List[Card] = Nil
          var newStackPair = stackPair
          for i <- 0 until a.ToDraw() do {
            var (newStackPair2: StackPair, cc: Card) = newStackPair.take1()
            newStackPair = newStackPair2
            cards = cards.prepended(cc)
          }

          this.copy(
            gameState = Normal(gameState.CurrentCard),
            playerStacks = playerStacks + (p -> playerStacks(p).push(cards)),
            stackPair = newStackPair,
            currentPlayerDrewCard = false
          )
        }
        case _ => {
          val (newStackPair, cc) = this.stackPair.take1()

          this.copy(
            playerStacks = playerStacks + (p -> playerStacks(p).push(cc)),
            stackPair = newStackPair,
            currentPlayerDrewCard = true
          )
        }
      }
    }
    case PlayerDrewCard(p, c) => {
      this.copy()
    }
    case PlayerFolded(p) => {
      assert(currentPlayerDrewCard)

      this.copy(
        players = this.gameState.played(this.players)
      ).emit(PlayerEndedTurn(p))
    }
    case _ => ???
  }
}

def gameStateFromInitialCard(card: Card): GameState = (card.rank, card.suit) match {
  case (Rank.Seven, _) => Seven(card)
  case _ => Normal(card)
}
