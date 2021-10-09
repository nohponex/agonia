package Agonia
package cards

object Rank extends Enumeration {
  type Rank = Value
  
  val Ace = Value("A")
  val Two = Value("2")
  val Three = Value("3")
  val Four = Value("4")
  val Five = Value("5")
  val Six = Value("6")
  val Seven = Value("7")
  val Eight = Value("8")
  val Nine = Value("9")
  val Ten = Value("10")
  val Jack = Value("J")
  val Queen = Value("Q")
  val King = Value("K")
}
