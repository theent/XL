package model;

import expr.*;
import gui.XL;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class XLModel {
  public static final int COLUMNS = 10, ROWS = 10;

  // String = Adress, typ B3, Cell är vad addressen innehåller
  private Map<String, Cell> contents;
  private ExprParser parser;
  private List<OnUpdateListener> observers = new ArrayList<>();
  private XL xl;

  public XLModel(XL xl){
    parser = new ExprParser();
    contents = new HashMap<>();
    this.xl = xl;
  }

  /**
   * Called when the code for a cell changes.
   *
   * @param address address of the cell that is being edited
   * @param text    the new code for the cell - can be raw text (starting with #) or an expression
   */
  public void update(CellAddress address, String text) {

    Cell c;
      if (text.length() == 0){
        c = new Empty();
      } else if (text.charAt(0) == '#'){
        c = new Comment(text.substring(1));
      } else{
        c = exprParser(text);
      }

    notifyObservers(address.toString(), c);
  }

  public void clearCell(String address){
    Cell c = new Empty();
    notifyObservers(address, c);
  }

  public void addObserver(OnUpdateListener o){
    observers.add(o);
  }

  private void notifyObservers(String address, Cell c){
    Map.Entry<String, Cell> entry = new AbstractMap.SimpleEntry<>(address, c);
    for (OnUpdateListener o : observers){
      o.onUpdate(entry);
    }

    contents.put(address, c);
  }

  // Får eventuellt ändras
  private Cell exprParser(String text) {
    try{
      Expr expr =  parser.build(text); // felhantering, beroende på vad det är för typ av expression
      return new Expression(expr.value(xl).value(), text);

    } catch (IOException e){
      e.printStackTrace();
    }

    return null;
  }

  public Cell getContent(String address){
    if (contents.containsKey(address))
      return contents.get(address);

    return new Empty();
  }

  public void loadFile(File file) throws FileNotFoundException {
    System.out.println("#### Load ####");
    XLBufferedReader reader = new XLBufferedReader(file);
    Map<String, String> tempMap = new HashMap<>();

    try {
      reader.load(tempMap);
    } catch (IOException e) {
      e.printStackTrace();
    }

    for(Map.Entry<String, String> entry : tempMap.entrySet()) {
      String address = entry.getKey();
      char rowC  = address.charAt(0);
      char colC  = address.charAt(1);
      int row = rowC - 65;
      int col = colC - 49;
      update(new CellAddress(row, col), entry.getValue());
    }
  }

  public void saveFile(File file) {
    System.out.println("#### Save ####");
    //TODO: implement this
    try {
      XLPrintStream printStream = new XLPrintStream(file.toString());
      printStream.save(contents.entrySet());
    } catch (FileNotFoundException e) {
      e.printStackTrace();

    }
  }

  private Map<String, Double> formatMap(Map<String, Cell> prov) {
    Map<String, Double> newOne = new HashMap<>();
    for (Map.Entry<String, Cell> entry : prov.entrySet()) {
      newOne.put(entry.getKey(), (double) entry.getValue().getContent());
    }
    return newOne;
  }
}
