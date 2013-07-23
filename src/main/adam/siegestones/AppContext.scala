package adam.siegestones

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.fxml.Initializable
import javafx.fxml.JavaFXBuilderFactory
import javafx.scene.Scene
import javafx.scene.layout.AnchorPane
import javafx.stage.Stage
import javafx.scene.control.Label
import adam.siegestones.controllers._
import adam.siegestones.logic.Logic
import adam.siegestones.models.Player
import adam.siegestones.models.Board
import javafx.scene.image.Image

class AppContext extends Application {

  private val MINIMUM_WINDOW_WIDTH = 600
  private val MINIMUM_WINDOW_HEIGHT = 400
  private var stage: Stage = null

  private val logic = new Logic(new Board,
    Array(new Player(AppContext.redStoneImg, AppContext.redTowerImg),
      new Player(AppContext.blueStoneImg, AppContext.blueTowerImg)))

  override def start(s: Stage) {
    stage = s
    stage.setTitle("SiegeStones")
    stage.setMinWidth(MINIMUM_WINDOW_WIDTH)
    stage.setMinHeight(MINIMUM_WINDOW_HEIGHT)
    showStart()
    stage.show()
  }

  private def showStart() {
    val start = replaceSceneContent("/resources/Board.fxml")
    start.setLogic(logic)

    stage.minHeightProperty.set(400)
    stage.minWidthProperty.set(400)
  }

  private def replaceSceneContent(fxml: String) = {
    val loader = new FXMLLoader
    val appContextClass = classOf[AppContext]
    val in = appContextClass.getResourceAsStream(fxml)
    loader.setBuilderFactory(new JavaFXBuilderFactory)
    loader.setLocation(appContextClass.getResource(fxml))
    val page = try {
      loader.load(in).asInstanceOf[AnchorPane];
    } finally {
      in.close();
    }
    val scene = new Scene(page);
    stage.setScene(scene);
    stage.sizeToScene();
    loader.getController.asInstanceOf[Controller];
  }
}

object AppContext {
  val redStoneImg = new Image("/resources/img/red-stone.png")
  val blueStoneImg = new Image("/resources/img/blue-stone.png")
  val redTowerImg = new Image("/resources/img/red-tower.png")
  val blueTowerImg = new Image("/resources/img/blue-tower.png")
  val neutralTowerImg = new Image("/resources/img/neutral-tower.png")
  val neutralStoneImg = new Image("/resources/img/neutral-stone.png")

  def main(args: Array[String]) {
    val appContextClass = classOf[AppContext]
    Application.launch(appContextClass, args: _*);
  }
}