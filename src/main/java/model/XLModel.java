package model;

import expr.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

public class XLModel implements Environment {
  public static final int COLUMNS = 10, ROWS = 10;

  // String = Adress, typ B3, Cell är vad addressen innehåller

  //TODO kanske ändra till cellContents?
  private Map<String, CellContent> contents;
  private ExprParser parser;
  private List<OnUpdateListener> observers = new ArrayList<>();

  public XLModel(){
    parser = new ExprParser();
    contents = new HashMap<>();
  }

  /**
   * Called when the code for a cell changes.
   *
   * @param address address of the cell that is being edited
   * @param text    the new code for the cell - can be raw text (starting with #) or an expression
   */
  public void update(String address, String text) {
    CellContent c;
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
    CellContent c = new Empty();
    notifyObservers(address, c);
  }

  public void addObserver(OnUpdateListener o){
    observers.add(o);
  }

  private void notifyObservers(String address, CellContent c){
    Map.Entry<String, CellContent> entry = new AbstractMap.SimpleEntry<>(address, c);
    for (OnUpdateListener o : observers){
      o.onUpdate(entry);
    }

    contents.put(address, c);
  }

  private CellContent exprParser(String text) {
    try{
      Expr expr =  parser.build(text);
      ExprResult res = expr.value(this);
      if (res.isError()){
        return new Comment(res.toString(), text);
      } else{
        return new Expression(res.value(), text);
      }

    } catch (IOException e){
      return new Comment(new ErrorResult(e.getMessage()).toString(), text);
    }
  }

  @Override
  public ExprResult value(String name) {
    name = name.toUpperCase();
    CellContent value = getContent(name);
    if (value != null && value instanceof Expression){
      return new ValueResult((double) value.getContent());
    } else{
      return new ErrorResult("missing value " + name);
    }
  }

  private void checkReferences(String currentAddress, LinkedList<String> visited){
    for (Map.Entry<String, CellContent> entry : contents.entrySet()){
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

  public CellContent getContent(String address){
    if (contents.containsKey(address))
      return contents.get(address);

    return new Empty();
  }

  public void loadFile(File file) throws FileNotFoundException {
    XLBufferedReader reader = new XLBufferedReader(file);
    Map<String, String> tempMap = new LinkedHashMap<>();
    try {
      reader.load(tempMap);
    } catch (IOException e) {
      e.printStackTrace();
    }

    for (Map.Entry<String, String> entry : tempMap.entrySet()) {
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
