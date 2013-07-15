package adam.siegestones.logic

import adam.siegestones.models.Player
import adam.siegestones.models.Stone
import adam.siegestones.models.Piece
import adam.siegestones.models.Board
import adam.siegestones.models.Tower

class Logic(
  private val board: Board,
  private var currentPlayer: Player,
  private var typeOfPiece: Piece) {

  def makeMove(p: (Int, Int)) {}

  def isMoveLegal(p: (Int, Int)) = {
    val isPieceLeft = typeOfPiece match {
      case _: Stone => currentPlayer.getLeftStones > 0
      case _: Tower => currentPlayer.getLeftTower > 0
    }
    isPieceLeft && board.get(p) == null
  }

  def getCurrentPlayer() { currentPlayer }
}