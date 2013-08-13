package adam.siegestones.models;

import org.junit.Assert._;

import org.junit.Before;
import org.junit.Test;

import org.mockito.Mockito._
import org.mockito.Matchers._

class BoardTest {
  var board: GenericBoard[Int] = null

  @Before
  def setUp {
    board = new GenericBoard[Int]
  }

  @Test
  def set {
    board set ((1, 0), 10)
    board set ((3, 3), 11)
    board set ((6, 3), 12)

    assertEquals(10, board get (1, 0))
    assertEquals(11, board get (3, 3))
    assertEquals(12, board get (6, 3))
  }

  @Test
  def setOutsideBoard {
    try {
      board set ((0, 1), 10)
      fail("Exception is not thrown")
    } catch {
      case e: IndexOutOfBoundsException => // success
    }

    try {
      board set ((5, 0), 11)
      fail("Exception is not thrown")
    } catch {
      case e: IndexOutOfBoundsException => // success
    }

    try {
      board set ((6, 6), 12)
      fail("Exception is not thrown")
    } catch {
      case e: IndexOutOfBoundsException => // success
    }

    try {
      board set ((-3, 3), 13)
      fail("Exception is not thrown")
    } catch {
      case e: IndexOutOfBoundsException => // success
    }
  }

  @Test
  def testNeighbours {
    val p1 = (1, 0)
    val p2 = (6, 3)
    val p3 = (3, 3)

    board set ((1, 1), 10)
    board set ((2, 1), 0)
    board set ((2, 0), 11)

    board set ((5, 2), 12)
    board set ((5, 3), 13)
    board set ((5, 4), 14)

    board set ((2, 2), 15)
    board set ((2, 3), 16)
    board set ((2, 4), 17)
    board set ((3, 2), 18)
    board set ((4, 3), 19)
    board set ((3, 4), 20)

    val expectedElems1 = Set(10, 0, 11)
    val expectedElems2 = Set(12, 13, 14)
    val expectedElems3 = Set(15, 16, 17, 18, 19, 20)

    assertEquals("Edege case 1", expectedElems1, board neighbours p1)
    assertEquals("Edege case 2", expectedElems2, board neighbours p2)
    assertEquals("Full case", expectedElems3, board neighbours p3)
  }

  @Test
  def neighboursFilterOutNulls {
    val pieceBoard = new Board
    val p = (0, 4)
    val stone = mock(classOf[Stone])

    pieceBoard set ((1, 3), stone)

    val expectedPieces = Set(stone)
    assertEquals(expectedPieces, pieceBoard neighbours (p))
  }

  @Test
  def neighbourTowers {
    val pieceBoard = new Board
    val p = (0, 4)
    val stone = mock(classOf[Stone])
    val tower1 = mock(classOf[Tower])
    val tower2 = mock(classOf[Tower])

    pieceBoard set ((1, 3), stone)
    pieceBoard set ((0, 3), tower1)
    pieceBoard set ((1, 5), tower2)

    val expectedTowers = Set(tower1, tower2)
    assertEquals(expectedTowers, pieceBoard neighbourTowers (p))
  }

  @Test
  def neighbourStones {
    val pieceBoard = new Board
    val p = (0, 4)
    val stone = mock(classOf[Stone])
    val tower1 = mock(classOf[Tower])
    val tower2 = mock(classOf[Tower])

    pieceBoard set ((1, 3), stone)
    pieceBoard set ((0, 3), tower1)
    pieceBoard set ((1, 5), tower2)

    val expectedStones = Set(stone)
    assertEquals(expectedStones, pieceBoard neighbourStones (p))
  }

  @Test
  def coordinatesOfElementExistAndNonExist {
    val pieceBoard = new Board
    val stone1 = mock(classOf[Stone])
    val stone2 = mock(classOf[Stone])
    val tower1 = mock(classOf[Tower])
    val tower2 = mock(classOf[Tower])

    pieceBoard set ((6, 3), stone1)
    pieceBoard set ((0, 3), stone2)
    pieceBoard set ((1, 5), tower1)

    assertEquals((6, 3), pieceBoard coordinatesOf stone1)
    assertEquals((-1, -1), pieceBoard coordinatesOf tower2)
  }

  @Test
  def toListStandardCase {
    val pieceBoard = new Board
    val stone1 = mock(classOf[Stone])
    val stone2 = mock(classOf[Stone])
    val tower = mock(classOf[Tower])

    pieceBoard set ((6, 3), stone1)
    pieceBoard set ((0, 3), stone2)
    pieceBoard set ((1, 5), tower)

    val actualList = pieceBoard.toList
    assertEquals(Set(stone1, stone2, tower), actualList.toSet)
  }

  @Test
  def toListEmptyBoard {
    val pieceBoard = new Board

    val actualList = pieceBoard.toList
    assertEquals(Set[Piece](), actualList.toSet)
  }

  @Test
  def testIsOnBoard() {
    assertTrue(board isOnBoard (0, 3))
    assertTrue(board isOnBoard (5, 1))
    assertTrue(board isOnBoard (3, 3))
    assertTrue(board isOnBoard (6, 3))

    assertFalse(board isOnBoard (5, 0))
    assertFalse(board isOnBoard (6, 0))
    assertFalse(board isOnBoard (0, 0))
    assertFalse(board isOnBoard (-1, 3))
    assertFalse(board isOnBoard (-3, -3))
  }

}
