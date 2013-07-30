package adam.siegestones.controllers

import java.net.URL
import java.util.ResourceBundle
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.fxml.Initializable
import javafx.scene.control.Button
import javafx.scene.control.ComboBox
import javafx.scene.control.TextField;
import adam.siegestones.AppContext

class MenuController extends Controller with Initializable {

  @FXML
  private def start(event: ActionEvent) {
    AppContext.showBoardView()
  }

  @FXML
  private def exit(event: ActionEvent) {
    // TODO
  }

  override def initialize(arg0: URL, arg1: ResourceBundle) {}
}