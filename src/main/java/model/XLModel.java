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
  private final ExprParser parser;
  private final List<OnUpdateObserver> observers;

  public XLModel(){
    parser = new ExprParser();
    contents = new HashMap<>();
    observers = new ArrayList<>();
  }

  /**
   * Called when the code for a cell changes.
   *
   * @param address address of the cell that is being edited
   * @param text    the new code for the cell - can be raw text (starting with #) or an expression
   */
  public void update(String address, String text) {
    notifyObservers(address, evaluateExpr(text));
    LinkedList<String> visited = new LinkedList<>();
    checkReferences(address, visited);
  }

  private Cell evaluateExpr(String text){
    Cell newCell = new ExprCell(text);
    try{
      newCell.evaluate(this);
    } catch (Error e){
      if (e instanceof EmptyError){
        newCell = new EmptyCell();
      } else{
        newCell = new TextCell(text, e.getMessage());
      }
    }

    return newCell;
  }

  public void clearCell(String address){
    Cell c = new EmptyCell();
    notifyObservers(address, c);
  }

  public void addObserver(OnUpdateObserver o){
    observers.add(o);
  }

  private void notifyObservers(String address, Cell c){
    Map.Entry<String, Cell> entry = new AbstractMap.SimpleEntry<>(address, c);
    for (OnUpdateObserver o : observers){
      o.onUpdate(entry);
    }

    contents.put(address, c);
  }

  @Override
  public ExprResult value(String name) {
    name = name.toUpperCase();
    Cell value = getCell(name);
    if (value instanceof ExprCell){
      return new ValueResult((value.value()));
    } else{
      return new ErrorResult("missing value " + name);
    }
  }

  private void checkReferences(String currentAddress, LinkedList<String> visited){
    for (Map.Entry<String, Cell> entry : contents.entrySet()){
      if (entry.getValue().expr().toUpperCase().contains(currentAddress)){
          if (visited.contains(entry.getKey())){
            for (String s : visited){
              //notifyObservers(s, new TextCell(contents.get(s).expr(), new ErrorResult("Circular Error").toString()));
              System.out.println("Address: " + s);
            }


            return;
          }

          visited.add(entry.getKey());
          notifyObservers(entry.getKey(), evaluateExpr(entry.getValue().expr()));
          checkReferences(entry.getKey(), visited);
      }
    }
  }

  public Cell getCell(String address){
    if (contents.containsKey(address))
      return contents.get(address);

    return new EmptyCell();
  }

  public void loadFile(File file) throws FileNotFoundException {
    XLBufferedReader reader = new XLBufferedReader(file);
    Map<String, String> loadRes = new LinkedHashMap<>();

    try {
      reader.load(loadRes);
    } catch (IOException e) {
      e.printStackTrace();
    }

    for(Map.Entry<String, Cell> entry : contents.entrySet()) {
      clearCell(entry.getKey());
    }

    for (Map.Entry<String, String> entry : loadRes.entrySet()) {
      update(entry.getKey(), entry.getValue());
    }
  }

  public void saveFile(File file) {
    try {
      XLPrintStream printStream = new XLPrintStream(file.toString());
      printStream.save(contents.entrySet());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
