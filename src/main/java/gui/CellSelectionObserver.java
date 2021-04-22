package gui;

/**
 * Functional interface for observing cell selection changes.
 */
public interface CellSelectionObserver {
  void selectionChanged(GridCell cell);
}
