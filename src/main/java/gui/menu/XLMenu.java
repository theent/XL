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
      xl.clearCell(xl.getAddress());
    });
    MenuItem clearAll = new MenuItem("ClearAll");
    clearAll.setOnAction(event -> {
      String letters = "ABCDEFGHIJ";
      for(int j = 0; j <10; j++) {
        for(int i = 0; i < 10; i++) {
          String adress = letters.substring(i, i + 1) + (j + 1);
          xl.clearCell(adress);
        }
      }
    });
    editMenu.getItems().addAll(clear, clearAll);
    getMenus().addAll(fileMenu, editMenu);
  }

}
