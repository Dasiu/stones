package adam.siegestones.logic;

import org.junit.Before
import org.junit.Test
import org.mockito.Matchers._
import org.mockito.Mockito._

import adam.siegestones.models.AIPlayer
import adam.siegestones.models.Board
import adam.siegestones.models.Player
import adam.siegestones.models.RealPlayer
import adam.siegestones.models.Stone
import adam.siegestones.models.Tower

class GameControllerTest {
  private var gameController: GameController = null
  private var logic: Logic = null

  @Before
  def setUp {
    logic = mock(classOf[Logic])
    gameController = new GameController(logic)
  }

  @Test
  def makeMovePlayerVsPlayer {
    when(logic.getCurrentPlayer).thenReturn(new RealPlayer)

    val (move, tower) = ((3, 3), new Tower)
    gameController makeMove (move, tower)

    verify(logic) makeMove (move, tower)
  }

  @Test
  def makeMovePlayerVsAI {
    val aiPlayer = mock(classOf[AIPlayer])
    when(aiPlayer.nextMove()).thenReturn(((-1, -1), null))
    when(logic.getCurrentPlayer).thenReturn(aiPlayer).thenReturn(mock(classOf[RealPlayer]))

    val (move, tower) = ((1, 4), new Tower)
    gameController makeMove (move, tower)

    verify(logic) makeMove (move, tower)
    verify(logic, times(2)) makeMove (anyObject(), anyObject())
  }

  @Test
  def makeMoveAIvsAI {
    val aiPlayer = mock(classOf[AIPlayer])
    when(aiPlayer.nextMove()).thenReturn(((-1, -1), null))
    when(logic.getCurrentPlayer).thenReturn(aiPlayer)
    when(logic.isGameOver).thenReturn(false).thenReturn(false).thenReturn(false).thenReturn(true)

    val (move, tower) = ((1, 4), new Tower)
    gameController makeMove (move, tower)

    verify(logic) makeMove (move, tower)
    verify(logic, times(4)) makeMove (anyObject(), anyObject())
  }

}
