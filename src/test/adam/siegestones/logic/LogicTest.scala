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
import adam.siegestones.models.Piece
import scala.collection.mutable.LinkedList
import scala.collection.mutable.MutableList
import adam.siegestones.models.GenericBoard

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

    logic = new Logic(board)
    logic.setPlayers(Array(player1, player2))
  }

  @Test
  def areMovesLegal {
    when(player1.getStonesNum).thenReturn(5)
    when(player1.getTowersNum).thenReturn(5)

    when(board.get(any(classOf[(Int, Int)]))).thenReturn(null)
    when(board.isOnBoard(anyObject())).thenCallRealMethod()

    assertTrue(logic.isMoveLegal((0, 3), new Stone(player1)))
    assertTrue(logic.isMoveLegal((3, 3), new Stone(player1)))
    assertTrue(logic.isMoveLegal((4, 6), new Tower))
    assertTrue(logic.isMoveLegal((6, 3), new Tower))
  }

  @Test
  def areMovesIllegal {
    val move1 = (0, 2)
    val move2 = (3, 1)
    val move3 = (5, 5)

    when(player1.getStonesNum).thenReturn(0)
    when(player1.getTowersNum).thenReturn(0)
    when(board get any(classOf[(Int, Int)])).thenReturn(null)

    assertFalse("Stones left out", logic.isMoveLegal((0, 3), new Stone(player1)))
    assertFalse("Stones left out", logic.isMoveLegal((3, 3), new Stone(player1)))
    assertFalse("Towers left out", logic.isMoveLegal((4, 6), new Tower))

    when(player1.getStonesNum).thenReturn(1)
    when(player1.getTowersNum).thenReturn(1)
    when(board get move1).thenReturn(new Stone(player1))
    when(board get move2).thenReturn(new Stone(player1))
    when(board get move3).thenReturn(new Tower)

    assertFalse("Field occupied 1", logic.isMoveLegal(move1, new Stone(player1)))
    assertFalse("Field occupied 2", logic.isMoveLegal(move2, new Stone(player1)))
    assertFalse("Field occupied 3", logic.isMoveLegal(move3, new Tower))
    assertFalse("Move outside board 1", logic.isMoveLegal((5, 0), new Tower))
    assertFalse("Move outside board 2", logic.isMoveLegal((6, 0), new Tower))

    when(player1.getTowersNum).thenReturn(0)

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

    when(board.isOnBoard(anyObject())).thenCallRealMethod()

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

    when(board.isOnBoard(anyObject())).thenCallRealMethod()

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

    when(board.isOnBoard(anyObject())).thenCallRealMethod()

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

    when(board.isOnBoard(anyObject())).thenCallRealMethod()

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

    when(board.isOnBoard(anyObject())).thenCallRealMethod()

    logic.makeMove(move, tower)
    verify(tower, atLeastOnce()).setOwner(null)
    verify(stone1).decrementPower
    verify(stone2).decrementPower
  }

  @Test
  def isGameOverWhenPlayerHasMoreThan4Towers {
    val winner = player1
    val loser = player2
    val pieces = createStonesAndTowers()
    for (_ <- 1 to 2) { pieces += createTowerMock(winner) }

    when(board.toList).thenReturn(pieces.toList)

    assertTrue(logic.isGameOver)
  }

  @Test
  def isGameOverWhenBothPlayersHas4TowersOrMore {
    val winner = player1
    val loser = player2
    val pieces = createStonesAndTowers()
    for (_ <- 1 to 2) { pieces += createTowerMock(winner) }
    pieces += createTowerMock(loser)

    when(board.toList).thenReturn(pieces.toList)

    assertTrue(logic.isGameOver)
  }

  @Test
  def isGameOverWhenBoardIsFull {
    val winner = player1
    val loser = player2
    val pieces = createStonesAndTowers()
    for (_ <- pieces.size until GenericBoard.SIZE) { pieces += createStoneMock(loser) }
    when(board.toList).thenReturn(pieces.toList)

    assertTrue(logic.isGameOver)
  }

  @Test
  def isGameOverWhenBothPlayersHas4Towers {
    val winner = player1
    val loser = player2
    val pieces = createStonesAndTowers()
    pieces += createTowerMock(winner)
    pieces += createTowerMock(loser)

    when(board.toList).thenReturn(pieces.toList)

    assertFalse(logic.isGameOver)
  }

  @Test
  def otherIsNotGameOverCases {
    val pieces = createStonesAndTowers()

    when(board.toList).thenReturn(List[Piece]())
    assertFalse("Empty board", logic.isGameOver)

    when(board.toList).thenReturn(pieces.toList)
    assertFalse("Both players controls 3 towers", logic.isGameOver)
  }

  private def createStonesAndTowers() = {
    val winner = player1
    val loser = player2
    val pieces = MutableList[Piece]()

    for (_ <- 1 to 3) { pieces += createTowerMock(winner) }
    for (_ <- 1 to 3) { pieces += createTowerMock(loser) }
    for (_ <- 1 to 6) { pieces += createStoneMock(winner) }
    for (_ <- 1 to 6) { pieces += createStoneMock(loser) }

    pieces
  }

  private def createPieceMock[T <: Piece](pieceType: Class[T], owner: Player): T =
    when(mock(pieceType).getOwner).thenReturn(owner).getMock()

  private def createTowerMock(owner: Player) = createPieceMock(classOf[Tower], owner)
  private def createStoneMock(owner: Player) = createPieceMock(classOf[Stone], owner)
}
