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

def startAGameOf2()(using dockGenerator: DeckGenerator): Game = {
  val players = Players(Player.Player1, 2)

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

  //todo state = f(card)
  //todo what about 8?
  Game(
    players = players,
    gameState = gameStateFromCard(card),
    playerStacks = playerStacks,
    stackPair = StackPair(stack, Stack(List(card))),
    LinearSeq(
      GameStarted(2),
      es.events.PlayerPlayedCard(Player.Player1, card),
      PlayerEndedTurn(Player.Player1),
    )
  )
}

case class Game(
     players: Players,
     gameState: GameState,
     playerStacks: Map[Player, CardStack],
     stackPair: StackPair,
     events: LinearSeq[Event] = LinearSeq()
) {
  def emit(event : Event): Game = {
    this.copy(events = events.appended(event)).apply(event)
  }

  def apply(event : Event): Game = {
    event match {
      case GameStarted(n): GameStarted => {
        this
      }
      case PlayerPlayedCard(_, card): PlayerPlayedCard => {
        this.copy(
          players = this.players.Next(), //not nessesary because can play 8 etc so either gamestate needs to provide it or have like a YieldEvent
        )
      }
      case PlayerPlayedCardAce(_, card, ofSuite): PlayerPlayedCardAce => {
        this.copy(
          players = this.players.Next(),
          gameState = Ace(currentCard = card, ofSuite),
        )
      }
      case _: PlayerEndedTurn => {
        var newGameState = gameStateFromCard(this.stackPair.peek())

        for ((p, s) <- this.playerStacks) {
           if (s.length() == 0) {
             newGameState = Ended()
           }
        }

        this.copy(
          players = this.players.Next(), //f(state)
          gameState = newGameState
        )
      }
      case _: PlayerDrewCard => ???
      case _: PlayerFolded => ???
    }
  }
}

def gameStateFromCard(card: Card): GameState = (card.rank, card.suit) match {
  case (Rank.Nine, _) => Seven(card)
  case (Rank.Seven, _) => Seven(card)
  case _ => Normal(card)
}
