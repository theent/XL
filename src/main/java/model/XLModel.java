package model;

import expr.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class XLModel implements Environment {
  public static final int COLUMNS = 10, ROWS = 10;

  // String = Adress, typ B3, CellContent är vad addressen innehåller
  private final Map<String, Cell> contents;      //Kvalificerad association
  private final List<OnUpdateObserver> observers;

  public XLModel(){
    contents = new LinkedHashMap<>();
    observers = new ArrayList<>();
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
      contents.put(address, eC);
      notifyObservers(address, eC.toString());
    } else if (text.charAt(0) == '#'){
      TextCell tC = new TextCell(text, text.substring(1));
      contents.put(address, tC);
      notifyObservers(address, tC.toString());
    } else{
      evaluateExpr(text, address);
    }

    HashSet<String> visited = new HashSet<>();

    checkReferences(address, visited);
    int varv = (contents.size() / 16) + 1;
    helpMe(varv, visited);


  }

  private void helpMe(int varv, HashSet<String> visited) {
    if(varv == 0) {
      return;
    }
    for (String s : visited){
            evaluateExpr(contents.get(s).expr(), s);
          }
    helpMe(varv - 1, visited);

  }

  /**
   * Evaluates the input and takes the proper procedures
   * @param text
   * @param address
   */
  private void evaluateExpr(String text, String address){
    Cell newCell = new ExprCell(text);
    contents.put(address, new CircularCell());
    ExprResult res = newCell.evaluate(this);

    if (res.isError()){
      newCell = new ErrorCell(text , res.error().contains("Missing value") ? "Circular Error" : res.error());
    }

    contents.put(address, newCell);
    notifyObservers(address, !res.isError() ? Double.toString(res.value()) : res.toString());
  }

  /**
   * Clears the content from a cell
   * @param address
   */
  public void clearCell(String address){
    Cell c = new EmptyCell();
    contents.put(address, c);
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
    Cell cell = getCell(name);

    try{
      return cell.evaluate(this);
    } catch (Error e){
      return new ErrorResult(e.getMessage() + " " + name);
    }
  }

  /**
   * Recursive method that updates all cells after a change has been made
   * @param currentAddress
   * @param visited
   */
  private void checkReferences(String currentAddress, HashSet<String> visited){
    for (Map.Entry<String, Cell> entry : contents.entrySet()){
      if (entry.getValue().expr().toUpperCase().contains(currentAddress)){
        if (!visited.add(entry.getKey())){
          /*for (String s : visited){
            evaluateExpr(contents.get(s).expr(), s);
          }*/

          return;
        }

        evaluateExpr(entry.getValue().expr(), entry.getKey());
        checkReferences(entry.getKey(), visited);
      }
    }
  }

  /**
   * Gets the content of specified cell
   * @param address
   * @return
   */
  public Cell getCell(String address){
    if (contents.containsKey(address))
      return contents.get(address);

    return new EmptyCell();
  }

  /**
   * Loads the data from a xml file to the program
   * @param file
   * @throws FileNotFoundException
   */
  public void loadFile(File file) throws FileNotFoundException {
    XLBufferedReader reader = new XLBufferedReader(file);

    for(Map.Entry<String, Cell> entry : contents.entrySet()) {
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
      printStream.save(contents.entrySet());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
