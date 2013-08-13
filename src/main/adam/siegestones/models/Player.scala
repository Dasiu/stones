package adam.siegestones.models

import javafx.scene.image.Image

class Player(private var stonesNum: Int, private var towersNum: Int) {
  private var stoneImage: Image = null
  private var towerImage: Image = null
  
  def this() {
    this(Player.DEFAULT_STONES_NUM, Player.DEFAULT_TOWERS_NUM)
  }

  def getStonesNum = stonesNum
  def getTowersNum = towersNum

  def decrementStones { stonesNum -= 1 }
  def decrementTowers { towersNum -= 1 }

  def getStoneImage = stoneImage
  def getTowerImage = towerImage

  def setStoneImage(sImage: Image) { stoneImage = sImage }
  def setTowerImage(tImage: Image) { towerImage = tImage }
}

object Player {
  val DEFAULT_STONES_NUM = 20
  val DEFAULT_TOWERS_NUM = 9
}