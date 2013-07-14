package adam.siegestones.models;

import org.junit.Assert._;

import org.junit.Before;
import org.junit.Test;

class BoardTest {
  var board: GenericBoard[Int] = null

  @Before
  def setUp {
    board = new GenericBoard[Int]
  }

  @Test
  def add {
    board set ((1, 0), 10)
    board set ((3, 3), 11)
    board set ((6, 3), 12)

    assertEquals(10, board get (1, 0))
    assertEquals(11, board get (3, 3))
    assertEquals(12, board get (6, 3))
  }

  @Test
  def addOutsideBoard {
    try {
      board set ((0, 1), 10)
      fail("Exception has not thrown")
    } catch {
      case e: IndexOutOfBoundsException => // success
    }

    try {
      board set ((5, 0), 11)
      fail("Exception has not thrown")
    } catch {
      case e: IndexOutOfBoundsException => // success
    }

    try {
      board set ((6, 6), 12)
      fail("Exception has not thrown")
    } catch {
      case e: IndexOutOfBoundsException => // success
    }

    try {
      board set ((-3, 3), 13)
      fail("Exception has not thrown")
    } catch {
      case e: IndexOutOfBoundsException => // success
    }
  }

}
