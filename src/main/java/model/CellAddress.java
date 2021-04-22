package model;

import com.google.common.base.Preconditions;

/**
 * Represents a cell address as a pair of zero-based column/row indices.
 */
public class CellAddress {
  public final int col, row;

  public CellAddress(int col, int row) {
    this.row = row;
    this.col = col;
  }

  /**
   * The column number must be greater or equal to 0 and less than 27.
   */
  public static String columnAddress(int column) {
    Preconditions.checkArgument(column >= 0, "column must be >= 0");
    Preconditions.checkArgument(column < 27, "column must be < 27");
    return Character.toString('A' + column);
  }

  /**
   * The row number must be greater than or equal to 0.
   */
  public static String rowAddress(int row) {
    Preconditions.checkArgument(row >= 0, "column must >= 0");
    return "" + (row + 1);
  }

  @Override public String toString() {
    return columnAddress(col) + rowAddress(row);
  }
}
