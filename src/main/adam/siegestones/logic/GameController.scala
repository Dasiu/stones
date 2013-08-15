package adam.siegestones.logic

import adam.siegestones.models.Piece
import adam.siegestones.models.AIPlayer
import adam.siegestones.models.RealPlayer
import scala.annotation.tailrec
import adam.siegestones.models.Player

final class GameController(private val logic: Logic) {
  /**
   * call if first move will be performed by real player.
   */
  @tailrec def makeMove(move: (Int, Int), piece: Piece) {
    if (!logic.isGameOver()) {
      logic makeMove (move, piece)
      logic getCurrentPlayer match {
        case ai: AIPlayer =>
          val (m, p) = ai.nextMove()
          makeMove(m, p)
        case _ => // do nothing
      }
    }

  }

  def start() {
    makeMove((-1, -1), null)
  }

  def getBoard = logic getBoard
  def getCurrentPlayer = logic getCurrentPlayer
}