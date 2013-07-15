package adam.siegestones.models

class Board extends GenericBoard[Piece] {
  def neighbourTowers(p: (Int, Int)) = neighbours(p).
    foldLeft(List[Tower]())((l: List[Tower], piece: Piece) => piece match {
      case t: Tower => t :: l
      case _ => l
    }) toSet

  def neighbourStones(p: (Int, Int)) = neighbours(p).
    foldLeft(List[Stone]())((l: List[Stone], piece: Piece) => piece match {
      case t: Stone => t :: l
      case _ => l
    }) toSet
}