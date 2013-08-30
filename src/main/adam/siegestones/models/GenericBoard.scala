package adam.siegestones.models

class GenericBoard[T: Manifest] {
  private val storage: Array[Array[T]] = Array(
    new Array(3),
    new Array(GenericBoard.HEIGHT),
    new Array(GenericBoard.HEIGHT),
    new Array(GenericBoard.HEIGHT),
    new Array(GenericBoard.HEIGHT),
    new Array(5),
    new Array(1))

  def get(field: (Int, Int)): T = {
    val (x, y) = normalizedCoordinates(field)
    storage(x)(y)
  }

  def set(field: (Int, Int), element: T) {
    val (x, y) = normalizedCoordinates(field)
    storage(x)(y) = element
  }

  def neighbours(element: (Int, Int)) = closestFields(element) map get filter (_ != null) toSet

  def coordinatesOf(element: T) = {
    val resultArray = for (
      i <- 0 until storage.length;
      val elemIdx = storage(i) indexOf element;
      if elemIdx != -1
    ) yield (i, elemIdx)

    if (resultArray.length == 0) (-1, -1)
    else unnormalizedCoordinates(resultArray.head)
  }

  private def closestFields(field: (Int, Int)) = {
    val (x: Int, y: Int) = field
    val possibleFields = if (y % 2 == 0) {
      (x, y - 1) :: (x - 1, y) :: (x, y + 1) :: (x + 1, y - 1) :: (x + 1, y) :: (x + 1, y + 1) :: Nil
    } else {
      (x - 1, y - 1) :: (x - 1, y) :: (x - 1, y + 1) :: (x, y - 1) :: (x + 1, y) :: (x, y + 1) :: Nil
    }
    possibleFields filter isOnBoard
  }

  /**
   * arg p: standard(unnormalized) coordinates
   */
  def isOnBoard(field: (Int, Int)) = field._1 match {
    case 0 => (2 to 4 toSet) contains field._2
    case 1 | 2 | 3 | 4 => (0 to 6 toSet) contains field._2
    case 5 => (1 to 5 toSet) contains field._2
    case 6 => field._2 == 3
    case x => false
  }

  private def processCoordinates(coord: (Int, Int))(op: (Int, Int) => Int) = coord._1 match {
    case 0 => (0, op(coord._2, 2))
    case 1 | 2 | 3 | 4 => coord
    case 5 => (5, op(coord._2, 1))
    case 6 => (6, op(coord._2, 3))
    case x => (x, coord._2)
  }

  private def normalizedCoordinates(p: (Int, Int)) = processCoordinates(p)(_ - _)

  private def unnormalizedCoordinates(p: (Int, Int)) = processCoordinates(p)(_ + _)

  def toList: List[T] = storage.reduce(_ ++ _).toList.filter(_ != null) toList
}

object GenericBoard {
  val HEIGHT = 7
  val WIDTH = 7
  val SIZE = 37
  val ALL_COORDINATES = List((0, 2), (0, 3), (0, 4), (1, 0), (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6),
    (2, 0), (2, 1), (2, 2), (2, 3), (2, 4), (2, 5), (2, 6),
    (3, 0), (3, 1), (3, 2), (3, 3), (3, 4), (3, 5), (3, 6),
    (4, 0), (4, 1), (4, 2), (4, 3), (4, 4), (4, 5), (4, 6),
    (5, 1), (5, 2), (5, 3), (5, 4), (5, 5),
    (6, 3))
}

