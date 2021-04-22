package util;

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
  public void load(Map<String, Object> map) throws IOException {
    try {
      while (ready()) {
        String string = readLine();
        int i = string.indexOf('=');
        // TODO
      }
    } catch (Exception e) {
      throw new XLException(e.getMessage());
    }
  }
}
