package gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import model.CellAddress;

/**
 * GUI component representing a Cell in the spreadsheet.
 */
public class GridCell extends Label {
  public final CellAddress address;
  private final Background normalBackground =
      new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY));
  private final Background hoverBackground =
      new Background(new BackgroundFill(Color.LIGHTYELLOW, CornerRadii.EMPTY, Insets.EMPTY));
  private final Background selectionBackground =
      new Background(new BackgroundFill(Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY));
  private final Border normalBorder = new Border(
      new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY,
          new BorderWidths(1)));
  private final Border hoverBorder = new Border(
      new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1)));
  private boolean selected = false;

  public GridCell(CellAddress address, CellSelectionObserver listener) {
    this.address = address;
    setMinHeight(20);
    setMinWidth(100);
    setMaxWidth(100);
    setFont(Font.font("Sans Serif", FontWeight.BOLD, 12));
    setBorder(normalBorder);
    setBackground(normalBackground);
    setOnMouseClicked(event -> {
      onSelect();
      listener.selectionChanged(this);
    });
    setOnMouseEntered(event -> {
      if (!selected) {
        setBackground(hoverBackground);
      }
      setBorder(hoverBorder);
    });
    setOnMouseExited(event -> {
      if (!selected) {
        setBackground(normalBackground);
      }
      setBorder(normalBorder);
    });
  }

  public void onSelect() {
    selected = true;
    setBackground(selectionBackground);
  }

  public void onDeselect() {
    selected = false;
    setBackground(normalBackground);
  }
}
