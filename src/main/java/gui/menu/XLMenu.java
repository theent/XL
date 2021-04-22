package gui.menu;

import gui.XL;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

public class XLMenu extends MenuBar {
  public XLMenu(XL xl, Stage stage) {
    Menu fileMenu = new Menu("File");
    MenuItem save = new SaveMenuItem(xl, stage);
    MenuItem load = new LoadMenuItem(xl, stage);
    MenuItem exit = new MenuItem("Exit");
    exit.setOnAction(event -> stage.close());
    fileMenu.getItems().addAll(save, load, exit);

    Menu editMenu = new Menu("Edit");
    MenuItem clear = new MenuItem("Clear");
    clear.setOnAction(event -> {
    });
    MenuItem clearAll = new MenuItem("ClearAll");
    clearAll.setOnAction(event -> {
    });
    editMenu.getItems().addAll(clear, clearAll);
    getMenus().addAll(fileMenu, editMenu);
  }
}
