package model;

import expr.*;
import gui.XL;
import javafx.scene.control.Cell;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XLModel {
  public static final int COLUMNS = 10, ROWS = 10;

  public static Map<String, String> prov = new HashMap<>();
  private Environment env;
  private XL xl;
  private ExprParser parser;

  public XLModel(XL xl){
    this.xl = xl;
    parser = new ExprParser();
  }

  /**
   * Called when the code for a cell changes.
   *
   * @param address address of the cell that is being edited
   * @param text    the new code for the cell - can be raw text (starting with #) or an expression
   */
  public void update(CellAddress address, String text) {
    env = name -> {
      if (prov.containsKey(name)){
        return new ValueResult(Double.parseDouble(exprParser(prov.get(name))));
      }

      return new ErrorResult("Can't find it");
    };

     if(text.length() == 0){
        xl.cellValueUpdated(address.toString(), "");
      } else if(text.charAt(0) == '#') {
        xl.cellValueUpdated(address.toString(), text.substring(1));
      } else {

       xl.cellValueUpdated(address.toString(), exprParser(text));

        // Vi måste göra någon form av rekursiv beräkning
       /*for (Map.Entry<CellAddress, String> ref : prov.entrySet()) {
          if (ref.getValue().contains(address.toString()) && !ref.getKey().equals(address)){
            Expr expr2 =  parser.build(text);
            double temp2 = expr2.value(env).value();
            xl.cellValueUpdated(ref.getKey().toString(), Double.toString(temp2));
            values.put(address.toString(), temp2);
          }
       }*/

      }
        prov.put(address.toString(), text);
  }

  private String exprParser(String text) {
    try{
      Expr expr =  parser.build(text); // felhantering, beroende på vad det är för typ av expression
      if (expr.value(env).isError())
        System.out.println("dwdwdw");

      return Double.toString(expr.value(env).value());

    } catch (IOException e){
      e.printStackTrace();
    }

    return null;
  }

  public void loadFile(File file) throws FileNotFoundException {
    System.out.println("#### load ####");
    System.out.println("#### " + file.toString() + " ####");
    XLBufferedReader reader = new XLBufferedReader(file);

    /*try {
      reader.load(values);
    } catch (IOException e) {
      e.printStackTrace();
    }

    for(Map.Entry<String, Double> entry : values.entrySet()) {
      String address = entry.getKey();
      char rowC  = address.charAt(0);
      char colC  = address.charAt(1);
      int row = rowC - 65;
      int col = colC - 49;
      update(new CellAddress(row, col), Double.toString(entry.getValue()), xl);
    }*/
  }

  public void saveFile(File file) {
    System.out.println("#### Save ####");
    System.out.println("#### " + file.toString() + " ####");
    //TODO: implement this
    try {
      XLPrintStream printStream = new XLPrintStream(file.toString());
      //printStream.save(values.entrySet());

    } catch (FileNotFoundException e) {
      e.printStackTrace();

    }
  }

  private Map<String, Double> formatMap(Map<CellAddress, String> prov) {
    Map<String, Double> newOne = new HashMap<>();
    for (Map.Entry<CellAddress, String> entry : prov.entrySet()) {
      newOne.put(entry.getKey().toString(), Double.parseDouble(entry.getValue()));
    }
    return newOne;
  }

}
