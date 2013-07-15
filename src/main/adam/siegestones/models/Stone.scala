package adam.siegestones.models

import adam.siegestones.logic.Logic

class Stone(private var owner: Player) extends Piece(owner) {
  private var power = Logic.INITIAL_STONE_POWER
  
  def decrementPower { power -= 1 }
  
  def getPower = power
}