package adam.siegestones.models;

import org.junit.Assert._;

import org.junit.Before;
import org.junit.Test;

class PlayerTest {

  @Before
  def setUp {
  }

  @Test
  def equalsTest {
    val player1 = new Player(5, 5)
    val player2 = new Player(5, 5)
    val player3 = new Player(5, 0)
    val player4 = new Player(-2, 5)
    
    assertTrue(player1 == player2)
    assertFalse(player1 == player3)
    assertFalse(player1 == player4)
    assertFalse(player1 == "test")
  }

}
