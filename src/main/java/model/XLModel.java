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
  public void update(String address, String text) {
    Cell c;
      if (text.length() == 0){
        c = new Empty();
      } else if (text.charAt(0) == '#'){
        c = new Comment(text.substring(1), text);
      } else{
        c = exprParser(text);
      }

    notifyObservers(address, c);
    LinkedList<String> visited = new LinkedList<>();
    checkReferences(address, visited);
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

  private Cell exprParser(String text) {
    try{
      Expr expr =  parser.build(text);
      ExprResult res = expr.value(xl);
      if (res.isError()){
        return new Comment(res.toString(), text);
      } else{
        return new Expression(res.value(), text);
      }

    } catch (IOException e){
      return new Comment(new ErrorResult(e.getMessage()).toString(), text);
    }
  }

  private void checkReferences(String currentAddress, LinkedList<String> visited){
    for (Map.Entry<String, Cell> entry : contents.entrySet()){
      if (entry.getValue().toString().toUpperCase().contains(currentAddress)){
          if (visited.contains(entry.getKey())){
            for (String s : visited){
              notifyObservers(s, new Comment(new ErrorResult("Circular Error").toString(), contents.get(s).toString()));
            }

            return;
          }

          visited.add(entry.getKey());
          notifyObservers(entry.getKey(), exprParser(entry.getValue().toString()));
          checkReferences(entry.getKey(), visited);
      }
    }
  }

  public Cell getContent(String address){
    if (contents.containsKey(address))
      return contents.get(address);

    return new Empty();
  }

  public void loadFile(File file) throws FileNotFoundException {
    XLBufferedReader reader = new XLBufferedReader(file);
    //Map<String, LinkedList<String>> tempMap = new LinkedHashMap<>();
    try {
      reader.load(this);
    } catch (IOException e) {
      e.printStackTrace();
    }

    //addNumbers(tempMap);
  }

  private void addNumbers(Map<String, LinkedList<String>> tempMap) {
    for (Map.Entry<String, LinkedList<String>> entry : tempMap.entrySet()) {
      System.out.println(entry.getValue());
      System.out.println(entry.getKey());

      for (String s : entry.getValue()){
        update(entry.getKey(), s);
      }

      /*if (entry.getValue().length() != 0) {
        if (entry.getValue().charAt(0) != '#') {
          if (!Character.isDigit(entry.getValue().charAt(0))) {
            newMap.put(entry.getKey(), entry.getValue());
          } else {
            updateWithCell(entry);
          }
        }
      }*/
    }
    //addRef(newMap);
  }

  private void addRef(Map<String, String> newMap) {
    Map<String, String> newMap2 = new HashMap<>();
    for (Map.Entry<String, String> entry : newMap.entrySet()) {
      if (checkIfValue(entry.getValue())) {
        updateWithCell(entry);
      } else {
        newMap2.put(entry.getKey(), entry.getValue());
      }
    }
    if (newMap2.size() != 0) {
      addRef(newMap2);
    }
  }

  private boolean checkIfValue(String text) {
    try {
      Cell c = exprParser(text);
      return true;
    } catch (Error e) {
    }
    return false;
  }

  private void updateWithCell(Map.Entry<String, String> entry) {
    String address = entry.getKey();
    char col1  = address.charAt(0);
    char row1  = address.charAt(1);
    int row2=Integer.parseInt(address.substring(1));
    int row;
    int col = col1 - 65;
    if(row2==10){
      row=9;
    } else {
      row = row1 - 49;
    }
    System.out.println(col +" "+ row);
    //update(new CellAddress(col, row), entry.getValue());
  }

  public void saveFile(File file) {
    //TODO: implement this
    try {
      XLPrintStream printStream = new XLPrintStream(file.toString());
      printStream.save(contents.entrySet());
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    }
  }
}
