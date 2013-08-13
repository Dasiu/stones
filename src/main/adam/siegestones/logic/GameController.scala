package adam.siegestones.logic

import adam.siegestones.models.Piece
import adam.siegestones.models.AIPlayer
import adam.siegestones.models.RealPlayer

class GameController(private val logic: Logic) {
	def makeMove(move: (Int, Int), piece: Piece) {
	  logic makeMove (move, piece)
	  logic getCurrentPlayer match {
	    case ai: AIPlayer => 
	      if (!logic.isGameOver()) {
	        val (m, p) = ai.nextMove()
	        makeMove(m, p)
	      }
	    case _: RealPlayer => // do nothing
	  }
	}
	
	def getBoard = logic getBoard
	def getCurrentPlayer = logic getCurrentPlayer
}