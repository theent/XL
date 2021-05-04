package model;

import expr.Expr;
import expr.ExprParser;
import expr.ExprResult;
import util.XLException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

public class XLBufferedReader extends BufferedReader {
  public XLBufferedReader(File file) throws FileNotFoundException {
    super(new FileReader(file));
  }

  // TODO Change Object to something appropriate Till Double
  public void load(Map<String, String> map) throws IOException {
    try {
      System.out.println("#### " + "Buffered Reader" +  " ####");
      while (ready()) {
        String string = readLine();
        int i = string.indexOf('=');
        String address = string.substring(0, i);
        String exp = string.substring(i + 1);


//        Cell cell = null;
//        if(exp.charAt(0) == '#') {
//          cell = new Comment(exp.substring(1)) ;
//        } else {
//          ExprParser parser = new ExprParser();
//          Expr expr = parser.build(exp);
//          cell = new Expression(, exp);
//        }

        System.out.print("#### " + address +  " ");
        System.out.println("#### " + exp +  " ####");

        map.put(address, exp);

        // TODO
      }
    } catch (Exception e) {
      throw new XLException(e.getMessage());
    }
  }
}
