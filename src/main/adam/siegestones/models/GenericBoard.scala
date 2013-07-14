package adam.siegestones.models

class GenericBoard[T] {
  private val HEIGHT = 7
  private val WIDTH = 7

  private val array: Array[Array[AnyRef]] = Array(
    new Array(3),
    new Array(HEIGHT),
    new Array(HEIGHT),
    new Array(HEIGHT),
    new Array(HEIGHT),
    new Array(5),
    new Array(1))

  def get(p: (Int, Int)): T =
    normalizedCoordinates(p) match { case (x: Int, y: Int) => array(x)(y).asInstanceOf[T] }

  def set(p: (Int, Int), v: T) {
    normalizedCoordinates(p) match {
      case (x: Int, y: Int) => {
        array(x)(y) = v match { case vv: AnyRef => vv }
      }
    }
  }
  
  private def normalizedCoordinates(p: (Int, Int)) = p._1 match {
      case 0 => (0, p._2 - 2)
      case 1|2|3|4 => p
      case 5 => (5, p._2 - 1)
      case 6 => (6, p._2 - 3)
      case x => (x, p._2)
    }
}

