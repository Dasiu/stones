package adam.siegestones.models

class GenericBoard[T] {
  private val array: Array[Array[Any]] = Array(
    new Array(3),
    new Array(GenericBoard.HEIGHT),
    new Array(GenericBoard.HEIGHT),
    new Array(GenericBoard.HEIGHT),
    new Array(GenericBoard.HEIGHT),
    new Array(5),
    new Array(1))

  def get(p: (Int, Int)): T =
    normalizedCoordinates(p) match { case (x: Int, y: Int) => array(x)(y).asInstanceOf[T] }

  def set(p: (Int, Int), v: T) {
    normalizedCoordinates(p) match {
      case (x: Int, y: Int) => {
        array(x)(y) = v match { case vv: Any => vv }
      }
    }
  }

  def neighbours(p: (Int, Int)) =
    closestFieldsCoordinates(p) filter isOnBoard map get filter (_ != null) toSet

  def coordinatesOf(elem: T) = {
    val resultArray = for (
      i <- 0 until array.length;
      val elemIdx = array(i) indexOf elem;
      if elemIdx != -1
    ) yield (i, elemIdx)
    
    if (resultArray.length == 0) (-1, -1)
    else unnormalizedCoordinates(resultArray.head)
  }

  private def closestFieldsCoordinates(p: (Int, Int)) = {
    val (x: Int, y: Int) = p
    if (y % 2 == 0) {
      (x, y - 1) :: (x - 1, y) :: (x, y + 1) :: (x + 1, y - 1) :: (x + 1, y) :: (x + 1, y + 1) :: Nil
    } else {
      (x - 1, y - 1) :: (x - 1, y) :: (x - 1, y + 1) :: (x, y - 1) :: (x + 1, y) :: (x, y + 1) :: Nil
    }
  }

  /**
   * arg p: standard(unnormalized) coordinates
   */
  private def isOnBoard(p: (Int, Int)) = p._1 match {
    case 0 => (2 to 4 toSet) contains p._2
    case 1 | 2 | 3 | 4 => (0 to 6 toSet) contains p._2
    case 5 => (1 to 5 toSet) contains p._2
    case 6 => 6 == p._2
    case x => false
  }

  private def processCoordinates(p: (Int, Int))(op: (Int, Int) => Int) = p._1 match {
    case 0 => (0, op(p._2, 2))
    case 1 | 2 | 3 | 4 => p
    case 5 => (5, op(p._2, 1))
    case 6 => (6, op(p._2, 3))
    case x => (x, p._2)
  }
    
  private def normalizedCoordinates(p: (Int, Int)) = processCoordinates(p)(_ - _)
  
  private def unnormalizedCoordinates(p: (Int, Int)) = processCoordinates(p)(_ + _)
}

object GenericBoard {
  val HEIGHT = 7
  val WIDTH = 7
}

