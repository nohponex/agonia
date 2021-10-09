package Agonia

import cards.{Card, Rank, Suit}

object Main {
 def main(args: Array[String]) = {

      var card: Card = Card(rank = Rank.Two, suit = Suit.Spades)
      var card2: Card = Card(rank = Rank.Two, suit = Suit.Spades)
      var card3: Card = Card(rank = Rank.Three, suit = Suit.Spades)

      println("Hello world")
      println(card)
      println(card.suit == card2.suit)
      println(card == card3)
   
   
 }
}
