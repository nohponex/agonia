package nohponex.agonia.fp.cards

enum Rank(string: String):   
  case Ace extends Rank("A")
  case Two extends Rank("2")
  case Three extends Rank("3")
  case Four extends Rank("4")
  case Five extends Rank("5")
  case Six extends Rank("6")
  case Seven extends Rank("7")
  case Eight extends Rank("8")
  case Nine extends Rank("9")
  case Ten extends Rank("10")
  case Jack extends Rank("J")
  case Queen extends Rank("Q")
  case King extends Rank("K")

  override def toString: String = this.string
