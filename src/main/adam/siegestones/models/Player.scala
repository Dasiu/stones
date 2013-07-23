package adam.siegestones.models

import javafx.scene.image.Image

class Player(
  private var stonesNum: Int,
  private var towersNum: Int,
  private val stoneImage: Image,
  private val towerImage: Image) {

  def this() {
    this(Player.DEFAULT_STONES_NUM, Player.DEFAULT_TOWERS_NUM, null, null)
  }
  
  def this(sImage: Image, tImage: Image) {
    this(Player.DEFAULT_STONES_NUM, Player.DEFAULT_TOWERS_NUM, sImage, tImage)
  }

  def getStonesNum = stonesNum
  def getTowersNum = towersNum

  def decrementStones { stonesNum }
  def decrementTowers { towersNum }

  def getStoneImage = stoneImage
  def getTowerImage = towerImage
}

object Player {
  val DEFAULT_STONES_NUM = 6
  val DEFAULT_TOWERS_NUM = 6
}