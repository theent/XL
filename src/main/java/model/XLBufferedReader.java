package model;

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

  // TODO Change Object to something appropriate
  public void load(Map<String, String> map) throws IOException {
    try {
      System.out.println("#### " + "Buffered Reader" +  " ####");
      while (ready()) {
        String string = readLine();
        int i = string.indexOf('=');
        String address = string.substring(0, i);
        String exp = string.substring(i + 1);

        System.out.print("#### " + address +  " ");
        System.out.println("#### " + exp +  " ####");

        map.put(address, exp);
        //map.computeIfAbsent(address, k -> new LinkedList<>()).add(exp);
      }
    } catch (Exception e) {
      throw new XLException(e.getMessage());
    }
  }
}
