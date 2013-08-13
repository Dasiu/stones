package adam.siegestones.controllers

import adam.siegestones.logic.Logic
import adam.siegestones.logic.GameController

abstract class Controller {
  protected var gameController: GameController = null
  
  def setGameController(gc: GameController) {
    gameController = gc
  }
}