package model;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map.Entry;
import java.util.Set;

public class XLPrintStream extends PrintStream {
  public XLPrintStream(String fileName) throws FileNotFoundException {
    super(fileName);
  }

  // TODO Change Object to something appropriate
  public void save(Set<Entry<String, Cell>> set) {
    System.out.println("#### Print Stream ####");
    for (Entry<String, Cell> entry : set) {
      System.out.println(entry.getKey());
      print(entry.getKey());
      print('=');
      println(entry.getValue());
      /*if(entry.getValue() instanceof Comment) {
        println("#" + entry.getValue());
      } else {
        println(entry.getValue());
      }*/
    }
    flush();
    close();
  }
}
