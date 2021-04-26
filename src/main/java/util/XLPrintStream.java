package util;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Map.Entry;
import java.util.Set;

public class XLPrintStream extends PrintStream {
  public XLPrintStream(String fileName) throws FileNotFoundException {
    super(fileName);
  }

  // TODO Change Object to something appropriate
  public void save(Set<Entry<String, Double>> set) { //TODO Ändrat till double från object
    for (Entry<String, Double> entry : set) {
      print(entry.getKey());
      print('=');
      println(entry.getValue());
    }
    flush();
    close();
  }
}
