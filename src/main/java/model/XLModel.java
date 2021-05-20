package model;

import expr.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class XLModel implements Environment {
  public static final int COLUMNS = 10, ROWS = 10;

  // String = Adress, typ B3, CellContent är vad addressen innehåller
  private final Map<String, Cell> cells;      //Kvalificerad association
  private final List<OnUpdateObserver> observers;

  public XLModel(){
    cells = new HashMap<>();
    observers = new ArrayList<>();

    for (int i = 0; i < ROWS; i++) {
      for (int j = 0; j < COLUMNS; j++) {
        cells.put(new CellAddress(i, j).toString(), new EmptyCell());
      }
    }
  }

  /**
   * Called when the code for a cell changes.
   *
   * @param address address of the cell that is being edited
   * @param text    the new code for the cell - can be raw text (starting with #) or an expression
   */
  public void update(String address, String text) {
    if (text.length() == 0){
      EmptyCell eC = new EmptyCell();
      cells.put(address, eC);
      notifyObservers(address, eC.toString());
    } else if (text.charAt(0) == '#'){
      TextCell tC = new TextCell(text, text.substring(1));
      cells.put(address, tC);
      notifyObservers(address, tC.toString());
    } else{
      evaluateExpr(text, address);
    }

    cells.forEach((key, value) -> {
      if (value instanceof ExprCell)
        evaluateExpr(value.inputText(), key);
    });
  }

  /**
   * Evaluates the input and takes the proper procedures
   * @param text
   * @param address
   */
  private void evaluateExpr(String text, String address){
    Cell newCell = new ExprCell(text);
    cells.put(address, new CircularCell());
    ExprResult res = newCell.evaluateExpr(this);

    cells.put(address, newCell);
    notifyObservers(address, !res.isError() ? Double.toString(res.value()) : res.toString());
  }

  /**
   * Clears the content from a cell
   * @param address
   */
  public void clearCell(String address){
    Cell c = new EmptyCell();
    cells.put(address, c);
    notifyObservers(address, c.toString());
  }

  /**
   * Adds a ovserver to the observerList
   * @param o
   */
  public void addObserver(OnUpdateObserver o){
    observers.add(o);
  }

  private void notifyObservers(String address, String value){
    Map.Entry<String, String> entry = new AbstractMap.SimpleEntry<>(address, value);
    for (OnUpdateObserver o : observers){
      o.onUpdate(entry);
    }
  }

  /**
   * Returns the result from a expression
   * @param name
   * @return
   */
  @Override
  public ExprResult value(String name) {
    name = name.toUpperCase();
    Cell cell = cells.get(name);

    try{
      return cell.evaluateExpr(this);
    } catch (Error e){
      return new ErrorResult(e.getMessage() + " " + name);
    }
  }

  /**
   * Gets the content of specified cell
   * @param address
   * @return
   */
  public String getCell(String address){
    return cells.get(address).inputText();
  }

  /**
   * Loads the data from a xml file to the program
   * @param file
   * @throws FileNotFoundException
   */
  public void loadFile(File file) throws FileNotFoundException {
    XLBufferedReader reader = new XLBufferedReader(file);

    for(Map.Entry<String, Cell> entry : cells.entrySet()) {
      clearCell(entry.getKey());
    }

    try {
      reader.load(this);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Saves the data from the program to a xml file
   * @param file
   */
  public void saveFile(File file) {
    try {
      XLPrintStream printStream = new XLPrintStream(file.toString());
      printStream.save(cells.entrySet());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
