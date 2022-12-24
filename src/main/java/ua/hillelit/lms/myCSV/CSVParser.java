package ua.hillelit.lms.myCSV;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;

/**
 * {@link CSVParser} is a class for parsing .csv files.
 *
 * @author Dmytro Trotsenko on 21.12.2022
 */
@NoArgsConstructor
public class CSVParser {

  /**
   * Parsing all lines from a csv file and return list words {@code List<String[]>}.
   *
   * @param lines     the text lines {@code List<String>}
   * @param separator the separator char or text {@code String}
   * @param skipLines skip lines number
   * @return the list separated by an array of words {@code List<String[]>}
   */
  public List<String[]> parse(List<String> lines, String separator, int skipLines) {
    if (lines == null || separator == null) {
      List<String[]> list = new ArrayList<>();
      list.add(new String[0]);
      return list;
    }
    return lines.stream()
        .skip(skipLines)
        .map(l -> l.split(separator))
        .collect(Collectors.toList());
  }

}
