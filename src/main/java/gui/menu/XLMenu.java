package gui.menu;

import gui.CellSelectionObserver;
import gui.XL;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.util.List;

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

      //TODO: clear selected cell
      System.out.println(xl.getAddress());
      xl.cellValueUpdated(xl.getAddress(), "");
    });
    MenuItem clearAll = new MenuItem("ClearAll");
    clearAll.setOnAction(event -> {
      //TODO: Clear all cells
      String letters = "ABCDEFGHIJ";
      for(int j = 0; j <10; j++) {
        for(int i = 0; i < 10; i++) {
          xl.cellValueUpdated(letters.substring(i, i + 1) + Integer.toString(j + 1), "");
        }
      }
    });
    editMenu.getItems().addAll(clear, clearAll);
    getMenus().addAll(fileMenu, editMenu);
  }

}
