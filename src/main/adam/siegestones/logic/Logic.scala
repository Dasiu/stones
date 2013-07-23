package adam.siegestones.logic

import adam.siegestones.models.Player
import adam.siegestones.models.Stone
import adam.siegestones.models.Piece
import adam.siegestones.models.Board
import adam.siegestones.models.Tower

class Logic(
  private val board: Board,
  private val players: Array[Player]) {
  private var currPlayerIdx = 0

  def makeMove(p: (Int, Int), piece: Piece) {
    if (isMoveLegal(p, piece)) {
      board set (p, piece)
      actualiseBoard(p, piece)
      piece match {
        case _: Stone => players(currPlayerIdx).decrementStones
        case _: Tower => players(currPlayerIdx).decrementTowers
      }
      currPlayerIdx = (currPlayerIdx + 1) % players.length
    }
  }

  /**
   * In this implementation setOwner in tower can be called once or more
   */
  private def actualiseBoard(p: (Int, Int), piece: Piece) {
    def evaluateOwner(t: Tower) = {
      val stones = board neighbourStones (board coordinatesOf (t))
      val playersWithItsPowersInNeighbourhood: Array[(Player, Int)] =
        players.map(pl => (pl, stones.foldLeft(0)(
          (sum, s) => 
            if (s.getOwner == pl) sum + s.getPower 
            else sum)))
      val maxPower = playersWithItsPowersInNeighbourhood map (_._2) max
      val playersWithMaxPower = playersWithItsPowersInNeighbourhood filter (_._2 == maxPower)
      val possibleOwners = playersWithMaxPower filter (_._2 > Logic.TOWER_TAKEOVER_LIMIT) map (_._1)

      if (possibleOwners.length == 1) possibleOwners.head
      else null
    }

    piece match {
      case s: Stone =>
        board neighbourTowers (p) foreach ((ss: Tower) => s.decrementPower)
        board neighbourTowers (p) foreach {
          (tt: Tower) => tt.setOwner(evaluateOwner(tt))
        }
      case t: Tower => board neighbourStones (p) foreach {
        (ss: Stone) =>
          ss.decrementPower
          board neighbourTowers (board coordinatesOf ss) foreach ((tt: Tower) => tt.setOwner(evaluateOwner(tt)))
      }
    }
  }

  def isMoveLegal(p: (Int, Int), piece: Piece) = {
    val isPieceLeft = piece match {
      case _: Stone => players(currPlayerIdx).getStonesNum > 0
      case _: Tower => players(currPlayerIdx).getTowersNum > 0
    }
    isPieceLeft && board.get(p) == null
  }

  def getCurrentPlayer = players(currPlayerIdx)
  
  def getBoard = board
}

object Logic {
  val INITIAL_STONE_POWER = 6
  val TOWER_TAKEOVER_LIMIT = 4
}