package adam.siegestones.logic;

import org.junit.Assert._
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito._
import org.mockito.Matchers._
import adam.siegestones.models.Player
import adam.siegestones.models.Stone
import adam.siegestones.models.Tower
import adam.siegestones.models.Board

class LogicTest {
  private var logic: Logic = null
  private var player1: Player = null
  private var player2: Player = null
  private var board: Board = null

  @Before
  def setUp {
    player1 = mock(classOf[Player])
    when(player1.getStonesNum).thenReturn(5)
    when(player1.getTowersNum).thenReturn(5)

    player2 = mock(classOf[Player])
    when(player2.getStonesNum).thenReturn(5)
    when(player2.getTowersNum).thenReturn(5)

    board = mock(classOf[Board])

    logic = new Logic(board, Array(player1, player2))
  }

  @Test
  def areMovesLegal {
    val move1 = (0, 3)
    val move2 = (3, 3)
    val move3 = (4, 6)

    when(player1.getStonesNum).thenReturn(5)
    when(player1.getTowersNum).thenReturn(5)

    when(board.get(any(classOf[(Int, Int)]))).thenReturn(null)

    assertTrue(logic.isMoveLegal(move1, new Stone(player1)))
    assertTrue(logic.isMoveLegal(move2, new Stone(player1)))
    assertTrue(logic.isMoveLegal(move3, new Tower))
  }

  @Test
  def areMovesIllegal {
    val move1 = (0, 2)
    val move2 = (3, 1)
    val move3 = (5, 5)
    val move4 = (0, 3)
    val move5 = (3, 3)
    val move6 = (4, 6)

    when(player1.getStonesNum).thenReturn(2).thenReturn(1).thenReturn(0)
    when(player1.getTowersNum).thenReturn(1).thenReturn(0)

    when(board get any(classOf[(Int, Int)])).thenReturn(null)
    when(board get move1).thenReturn(new Stone(player1))
    when(board get move2).thenReturn(new Stone(player1))
    when(board get move3).thenReturn(new Tower)

    assertFalse("Field occupied 1", logic.isMoveLegal(move1, new Stone(player1)))
    assertFalse("Field occupied 2", logic.isMoveLegal(move2, new Stone(player1)))
    assertFalse("Field occupied 3", logic.isMoveLegal(move3, new Tower))
    assertFalse("Stones left out", logic.isMoveLegal(move4, new Stone(player1)))
    assertFalse("Stones left out", logic.isMoveLegal(move5, new Stone(player1)))
    assertFalse("Towers left out", logic.isMoveLegal(move6, new Tower))
    assertFalse("Field occupied and towers left out", logic.isMoveLegal(move3, new Tower))
  }

  @Test
  def makeLegalMove {
    val (move, stone) = ((0, 2), new Stone(player1))

    when(player1.getStonesNum).thenReturn(5)

    when(board get any(classOf[(Int, Int)])).thenReturn(null)
    when(board neighbourStones any(classOf[(Int, Int)])).thenCallRealMethod()
    when(board neighbours any(classOf[(Int, Int)])).thenCallRealMethod()
    when(board neighbourTowers any(classOf[(Int, Int)])).thenCallRealMethod()

    logic.makeMove(move, stone)

    verify(board).set(move, stone)
    verify(player1).decrementStones
    assertEquals("Check if next player turn is set", player2, logic.getCurrentPlayer)
  }

  @Test
  def towerTakeOverAfterTowerMove {
    val (move, tower) = ((0, 4), mock(classOf[Tower]))

    val (coord1, stone1) = ((1, 4), mock(classOf[Stone]))
    when(stone1.getPower).thenReturn(5)
    when(stone1.getOwner).thenReturn(player1)

    val (coord2, stone2) = ((2, 5), mock(classOf[Stone]))
    when(stone2.getPower).thenReturn(6)
    when(stone2.getOwner).thenReturn(player2)

    when(board neighbourStones move).thenReturn(Set(stone1))
    when(board neighbourTowers coord1).thenReturn(Set(tower))
    when(board coordinatesOf tower).thenReturn(move)
    when(board coordinatesOf stone1).thenReturn(coord1)

    logic.makeMove(move, tower)
    verify(tower, atLeastOnce()).setOwner(player1)
    verify(stone1).decrementPower
  }

  @Test
  def towerTakeOverAfterStoneMove {
    val (move, stone) = ((1, 4), mock(classOf[Stone]))
    when(stone.getPower).thenReturn(5)
    when(stone.getOwner).thenReturn(player1)

    val (coord, tower) = ((0, 4), mock(classOf[Tower]))
    when(board neighbourStones coord).thenReturn(Set(stone))
    when(board neighbourStones move).thenReturn(Set[Stone]())
    when(board neighbourTowers move).thenReturn(Set(tower))
    when(board coordinatesOf tower).thenReturn(coord)
    when(board coordinatesOf stone).thenReturn(move)

    logic.makeMove(move, stone)
    verify(tower, atLeastOnce()).setOwner(player1)
    verify(stone).decrementPower
  }

  @Test
  def towerGoesNeutralNotEnoughPower {
    val (move, tower1) = ((2, 4), mock(classOf[Tower]))
    val (coord1, stone1) = ((1, 4), mock(classOf[Stone]))
    when(stone1.getPower).thenReturn(4)
    when(stone1.getOwner).thenReturn(player1)

    val (coord2, stone2) = ((2, 5), mock(classOf[Stone]))
    when(stone2.getPower).thenReturn(5)
    when(stone2.getOwner).thenReturn(player2)

    val (towerCoord, tower2) = ((0, 4), mock(classOf[Tower]))
    when(board neighbourStones towerCoord).thenReturn(Set(stone1))
    when(board neighbourStones move).thenReturn(Set(stone1, stone2))
    when(board neighbourTowers coord1).thenReturn(Set(tower1, tower2))
    when(board neighbourTowers coord2).thenReturn(Set(tower1))
    when(board coordinatesOf tower1).thenReturn(move)
    when(board coordinatesOf tower2).thenReturn(towerCoord)
    when(board coordinatesOf stone1).thenReturn(coord1)
    when(board coordinatesOf stone2).thenReturn(coord2)

    logic.makeMove(move, tower1)

    verify(tower2, atLeastOnce()).setOwner(null)
    verify(tower1, atLeastOnce()).setOwner(player2)
    verify(stone1).decrementPower
    verify(stone2).decrementPower
  }

  @Test
  def towerGoesNeutralSameAmountOfPower {
    val (coord1, stone1) = ((1, 4), mock(classOf[Stone]))
    when(stone1.getPower).thenReturn(5)
    when(stone1.getOwner).thenReturn(player1)

    val (coord2, stone2) = ((2, 5), mock(classOf[Stone]))
    when(stone2.getPower).thenReturn(5)
    when(stone2.getOwner).thenReturn(player2)

    val (move, tower) = ((2, 4), mock(classOf[Tower]))
    when(board neighbourStones move).thenReturn(Set(stone1, stone2))
    when(board neighbourTowers coord1).thenReturn(Set(tower))
    when(board neighbourTowers coord2).thenReturn(Set(tower))
    when(board coordinatesOf tower).thenReturn(move)
    when(board coordinatesOf stone1).thenReturn(coord1)
    when(board coordinatesOf stone2).thenReturn(coord2)

    logic.makeMove(move, tower)
    verify(tower, atLeastOnce()).setOwner(null)
    verify(stone1).decrementPower
    verify(stone2).decrementPower
  }
}
