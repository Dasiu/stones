package adam.siegestones.controllers

import adam.siegestones.logic.Logic

abstract class Controller {

  protected var logic: Logic = null
  
  def setLogic(l: Logic) {
    logic = l
  }
}