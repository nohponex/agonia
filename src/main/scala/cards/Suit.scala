package Agonia
package cards

object Suit extends Enumeration {
  type Suit = Value
  val Hearts  = Value("♥")
  val Diamonds  = Value("♦")
  val Spades  = Value("♠")
  val Clubs = Value("♣")
}
