import java.awt.Point
import java.net.URL
import java.util.HashMap
import java.util.ResourceBundle
import javafx.beans.property.SimpleObjectProperty
import javafx.concurrent.Task
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.GridPane
import adam.siegestones.controllers.Controller
import adam.siegestones.models.Stone
import adam.siegestones.models.Tower
import adam.siegestones.models.Piece
import adam.siegestones.AppContext

class BoardController extends Controller with Initializable {

  @FXML
  private val boardGridPane: GridPane = null

  private var typeOfNewPiece: Piece = new Tower

  override def initialize(arg0: URL, arg1: ResourceBundle) {
  }

  @FXML private def onFieldButtonAction(event: ActionEvent) {
    event.getSource match {
      case firedButton: Button =>
        val gridRow = GridPane.getRowIndex(firedButton)
        val gridColumn = GridPane.getColumnIndex(firedButton)
        val (boardRow, boardColumn) = toBoardCoordinates((gridRow, gridColumn))

        gameController makeMove ((boardRow, boardColumn), createNewPiece())

        drawBoard()
    }
  }

  @FXML private def useStoneInNextMoves(event: ActionEvent) {
    typeOfNewPiece = new Stone(null)
  }

  @FXML private def useTowerInNextMoves(event: ActionEvent) {
    typeOfNewPiece = new Tower
  }

  @FXML private def onMenuButtonAction(event: ActionEvent) {
  }

  @FXML private def aiNextMoveButton(event: ActionEvent) {
  }

  private def drawBoard() {
    boardGridPane.getChildren().toArray.foreach {
      _ match {
        case button: Button =>
          val gridRow = GridPane.getRowIndex(button)
          val gridColumn = GridPane.getColumnIndex(button)
          val boardCoord = toBoardCoordinates((gridRow, gridColumn))
          (gameController getBoard) get boardCoord match {
            case tower: Tower =>
              val owner = tower.getOwner
              val imageView = if (owner != null) owner.getTowerImage else AppContext.neutralTowerImg
              button.setGraphic(new ImageView(imageView))
            case stone: Stone =>
              val owner = stone.getOwner
              val imageView = if (owner != null) owner.getStoneImage else AppContext.neutralStoneImg
              button.setGraphic(new ImageView(imageView))
            case null => button.setGraphic(null)
          }
      }
    }
  }

  private def toBoardCoordinates(gridCoord: (Int, Int)) = {
    val (gridRow, gridColumn) = gridCoord
    (gridRow / 2, gridColumn / 2)
  }

  private def createNewPiece(): Piece = typeOfNewPiece match {
    case _: Stone => new Stone(gameController getCurrentPlayer)
    case _: Tower => new Tower
  }
}
