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

class AppContext extends Application {

  private val MINIMUM_WINDOW_WIDTH = 600
  private val MINIMUM_WINDOW_HEIGHT = 400
  private var stage: Stage = null

  override def start(s: Stage) {
//    val root = new AnchorPane
//    root.getChildren.add(new Label("Hello world!"))
//
//    s.setScene(new Scene(root, 300, 300))
//    s.show()
        stage = s
        stage.setTitle("SiegeStones")
        stage.setMinWidth(MINIMUM_WINDOW_WIDTH)
        stage.setMinHeight(MINIMUM_WINDOW_HEIGHT)
        showStart()
        stage.show()
  }

  private def showStart() {
    val start = replaceSceneContent("../../resources/Menu.fxml")
    stage.minHeightProperty.set(400)
    stage.minWidthProperty.set(400)
  }

  private def replaceSceneContent(fxml: String) = {
    val loader = new FXMLLoader
    val appContextClass = classOf[AppContext]
    //    val appContextClass = AppContext.getClass.asInstanceOf[Class[AppContext]]
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
  def main(args: Array[String]) {
    val appContextClass = classOf[AppContext]
    Application.launch(appContextClass, args: _*);
  }
}