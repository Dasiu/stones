package adam.siegestones.models

abstract class Piece(private var owner: Player) {
  def setOwner(p: Player) { owner = p }
  def getOwner = owner
}