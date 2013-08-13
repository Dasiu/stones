package adam.siegestones.models

import adam.siegestones.logic.Logic

abstract class AIPlayer(private val logic: Logic) extends Player {
	def nextMove(): ((Int, Int), Piece)
}