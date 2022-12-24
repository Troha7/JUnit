package ua.hillelit.lms.myCSV;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.NoArgsConstructor;

/**
 * {@link CSVReader} is a class for read .csv files.
 *
 * @author Dmytro Trotsenko on 19.12.2022
 */
@NoArgsConstructor
public class CSVReader {

  /**
   * Read all lines from a csv file and return {@code List<String>}.
   *
   * @param path the path to the file
   * @return the lines from the file as a {@code List<String>}
   */
  public List<String> read(String path) {

    try {
      return Files.readAllLines(Paths.get(path));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }


}
