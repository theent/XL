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

  // TODO Change Object to something appropriate Till Double
  public void load(Map<String, Double> map) throws IOException {
    try {
      System.out.println("#### " + "address + exp for each cell" +  " ####");
      while (ready()) {
        String string = readLine();
        int i = string.indexOf('=');
        String address = string.substring(0, i);
        String exp = string.substring(i + 1);
        double d = Double.parseDouble(exp);

        System.out.print("#### " + address +  " ####");
        System.out.print("#### " + exp +  " ####");
        System.out.println("#### " + d +  " ####");

        map.put(address, d);

        // TODO
      }


    } catch (Exception e) {
      throw new XLException(e.getMessage());
    }
  }
}
