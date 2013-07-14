package adam.siegestones.logic

import adam.siegestones.models.Player
import adam.siegestones.models.Stone

class GameMechanics(
		private var currentPlayer: Player,
		private var typeOfPiece: Stone
		) {
	
	def makeMove(coordinates: (Int, Int)) {
	  val piecesLeft = typeOfPiece match {
	    case _: Stone => currentPlayer.getLeftStones
	  }
	}
	
	def getCurrentPlayer() {currentPlayer}
}