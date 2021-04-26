package model;

import expr.Environment;
import expr.Expr;
import expr.ExprParser;
import expr.ValueResult;
import gui.XL;
import util.XLBufferedReader;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class XLModel {
  public static final int COLUMNS = 10, ROWS = 10;

  public static Map<CellAddress, String> prov = new HashMap<>();
  private Map<String, Double> values = new HashMap<>();


  /**
   * Called when the code for a cell changes.
   *
   * @param address address of the cell that is being edited
   * @param text    the new code for the cell - can be raw text (starting with #) or an expression
   */
  public void update(CellAddress address, String text, XL xl) {
    ExprParser parser = new ExprParser();

    Environment env = name -> {
      if (values.containsKey(name)){
        return new ValueResult(values.get(name));
      }

      return null;
    };

    try {
     if(text.length() == 0){
        xl.cellValueUpdated(address.toString(), "");
        values.remove(address.toString());
      } else if(text.charAt(0) == '#') {
        xl.cellValueUpdated(address.toString(), text.substring(1));
      } else {
        Expr a =  parser.build(text);
        double temp = a.value(env).value();
        xl.cellValueUpdated(address.toString(), Double.toString(temp));
        values.put(address.toString(), temp);
      }
        prov.put(address, text);
    } catch (IOException e){
      e.printStackTrace();
    }
  }

  public void loadFile(File file) throws FileNotFoundException {
    XLBufferedReader reader = new XLBufferedReader(file);
  }

  public void saveFile(File file) {
    //TODO: implement this
  }
}
