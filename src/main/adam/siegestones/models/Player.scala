package adam.siegestones.models

class Player(
  private var stonesNum: Int,
  private var towersNum: Int) {

  def this() {
    this(Player.DEFAULT_STONES_NUM, Player.DEFAULT_TOWERS_NUM)
  }

  def getStonesNum = stonesNum
  def getTowersNum = towersNum

  def decrementStones { stonesNum }
  def decrementTowers { towersNum }
  
  override def equals(that: Any) = that match {
    case player: Player => stonesNum == player.getStonesNum && towersNum == player.getTowersNum
    case _ => false
  }
}

object Player {
  val DEFAULT_STONES_NUM = 6
  val DEFAULT_TOWERS_NUM = 6
}