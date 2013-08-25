package adam.siegestones.models

import java.util.Random
import adam.siegestones.logic.Logic
import scala.annotation.tailrec

/**
 * Simple AI, which returns random moves. In case game is over AI will be looping. 
 */
final class RandomAIPlayer(private val logic: Logic, private val generator: Random) extends AIPlayer(logic) {
  @tailrec override def nextMove(): ((Int, Int), Piece) = {
	  val (x, y, pieceId) = drawMoveNumbers()
	  val piece = pieceId match {
	    case 0 => new Stone(this)
	    case 1 =>  new Tower
	  }
	  if (logic isMoveLegal ((x, y), piece)) ((x,y), piece)
	  else nextMove()
  }
  
  private def drawMoveNumbers() = {
    val x = generator.nextInt(Integer.MAX_VALUE) % GenericBoard.HEIGHT
    val y = generator.nextInt(Integer.MAX_VALUE) % GenericBoard.WIDTH
    val pieceId = generator.nextInt(Integer.MAX_VALUE) % 2
    (x, y, pieceId)
  }
}