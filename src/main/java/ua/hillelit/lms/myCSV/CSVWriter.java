package ua.hillelit.lms.myCSV;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.NoArgsConstructor;

/**
 * {@link CSVWriter} is a class for create .csv files.
 * @author Dmytro Trotsenko on 21.12.2022
 */
@NoArgsConstructor
public class CSVWriter {

  /**
   * Write lines of text to a csv file.
   *
   * @param path the path to the file
   * @param lines the text lines {@code List<String>}
   */
  public void write(String path, List<String> lines){
    try {
      Files.write(Paths.get(path), lines);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
