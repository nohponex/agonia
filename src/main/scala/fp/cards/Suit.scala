package nohponex.agonia.fp.cards

enum Suit(string: String):
  case Hearts  extends Suit("♥")
  case Diamonds  extends Suit("♦")
  case Spades  extends Suit("♠")
  case Clubs extends Suit("♣")

  override def toString: String = this.string
