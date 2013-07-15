package adam.siegestones.logic;

import org.junit.Assert._
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito._
import adam.siegestones.models.Player
import adam.siegestones.models.Stone
import adam.siegestones.models.Tower
import adam.siegestones.models.Board

class LogicTest {
  var logicStone: Logic = null
  var logicTower: Logic = null

  @Before
  def setUp {
    val player = mock(classOf[Player])
    when(player.getLeftStones).thenReturn(2).thenReturn(1).thenReturn(0)
    when(player.getLeftTower).thenReturn(10).thenReturn(0)

    val board = mock(classOf[Board])
    when(board.get((0, 3))).thenReturn(null)
    when(board.get((3, 3))).thenReturn(null)
    when(board.get((4, 6))).thenReturn(null)
    when(board.get((0, 2))).thenReturn(new Stone)
    when(board.get((3, 1))).thenReturn(new Stone)
    when(board.get((5, 5))).thenReturn(new Tower)

    logicStone = new Logic(board, player, new Stone)
    logicTower = new Logic(board, player, new Tower)
  }

  @Test
  def legalMoves {
    val move1 = (0, 3)
    val move2 = (3, 3)
    val move3 = (4, 6)

    assertTrue(logicStone.isMoveLegal(move1))
    assertTrue(logicStone.isMoveLegal(move2))
    assertTrue(logicTower.isMoveLegal(move3))
  }

  @Test
  def illegalMoves {
    val move1 = (0, 2)
    val move2 = (3, 1)
    val move3 = (5, 5)
    val move4 = (0, 3)
    val move5 = (3, 3)
    val move6 = (4, 6)

    assertFalse("Field occupied 1", logicStone.isMoveLegal(move1))
    assertFalse("Field occupied 2", logicStone.isMoveLegal(move2))
    assertFalse("Field occupied 3", logicTower.isMoveLegal(move3))
    assertFalse("Stones left out", logicStone.isMoveLegal(move4))
    assertFalse("Stones left out", logicStone.isMoveLegal(move5))
    assertFalse("Towers left out", logicTower.isMoveLegal(move6))
    assertFalse("Field occupied and Towers left out", logicTower.isMoveLegal(move3))
  }

}
