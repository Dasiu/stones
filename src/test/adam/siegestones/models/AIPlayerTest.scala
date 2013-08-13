package adam.siegestones.models;

import java.util.Random

import org.junit.Assert._
import org.junit.Before
import org.junit.Test
import org.mockito.Matchers._
import org.mockito.Mockito._

import adam.siegestones.logic.Logic

class AIPlayerTest {
  private var generator: Random = null
  private var logic: Logic = null

  @Before
  def setUp {
  }

  private def setUpRandomAIPlayerTests() {
    generator = mock(classOf[Random])
    logic = mock(classOf[Logic])
  }

  @Test
  def nextRandomMove {
    setUpRandomAIPlayerTests()
    when(logic.isMoveLegal(anyObject(), anyObject())).thenReturn(true)
    when(generator.nextInt(any())).thenReturn(3)
    val aiPlayer = new RandomAIPlayer(logic, generator)

    val move = (3, 3)
    val (nextMove, nextPiece) = aiPlayer.nextMove()

    assertEquals(move, nextMove)
    assertTrue(nextPiece.isInstanceOf[Tower])
  }

  @Test
  def nextMoveMustBeLegal {
    setUpRandomAIPlayerTests()
    val illegalMove = (0, 0)
    val legalMove = (1, 4)
    when(generator.nextInt(any())).thenReturn(0).thenReturn(0).thenReturn(11).thenReturn(1).thenReturn(4)
    when(logic.isMoveLegal(anyObject(), anyObject())).thenReturn(false).thenReturn(true)

    val aiPlayer = new RandomAIPlayer(logic, generator)

    val (generatedMove, _) = aiPlayer.nextMove()

    assertEquals(legalMove, generatedMove)
  }
}
