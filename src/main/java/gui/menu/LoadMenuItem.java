package gui.menu;

import gui.XL;
import javafx.scene.control.MenuItem;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class LoadMenuItem extends MenuItem {
  public LoadMenuItem(XL xl, Stage stage) {
    super("Load");
    setOnAction(event -> {
      FileChooser fileChooser = new FileChooser();
      fileChooser.getExtensionFilters()
          .add(new FileChooser.ExtensionFilter("XL files (*.xl)", "*.xl"));
      File file = fileChooser.showOpenDialog(stage);
      if (file != null) {
        // TODO
        xl.loadFile(file);
      }
    });
  }
}
