package adam.siegestones.models

class Board extends GenericBoard[Piece] {
  def neighbourTowers(piece: (Int, Int)) = neighboursOfType[Tower](piece)

  def neighbourStones(piece: (Int, Int)) = neighboursOfType[Stone](piece)

  private def neighboursOfType[T <: Piece: Manifest](piece: (Int, Int)) = neighbours(piece).
    foldLeft(List[T]())((list, piece) => piece match {
      case element: T => element :: list
      case _ => list
    }) toSet
}