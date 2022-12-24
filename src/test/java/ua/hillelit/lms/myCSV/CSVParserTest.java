package ua.hillelit.lms.myCSV;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * Test class for {@link CSVParser}.
 *
 * @author Dmytro Trotsenko on 21.12.2022
 */
public class CSVParserTest {

  private final String ORDER = "/path/to/file.csv";
  CSVReader csvReader = Mockito.mock(CSVReader.class);
  private final CSVParser parser = new CSVParser();

  @Test
  void testCsvLines() {
    List<String> lines = Arrays.asList("a;", "a;b;", "a;b;c;", "a;b;c;d;");

    Mockito.when(csvReader.read(ORDER)).thenReturn(lines);
    List<String> orderLines = csvReader.read(ORDER);
    List<String[]> result = parser.parse(orderLines, ";", 0);

    assertArrayEquals(new String[]{"a"}, result.get(0));
    assertArrayEquals(new String[]{"a", "b"}, result.get(1));
    assertArrayEquals(new String[]{"a", "b", "c"}, result.get(2));
    assertArrayEquals(new String[]{"a", "b", "c", "d"}, result.get(3));
  }

  @Test
  void testCsvLinesIsEmpty() {
    List<String> lines = Arrays.asList(";", " ;;", "", ";;;d;");

    Mockito.when(csvReader.read(ORDER)).thenReturn(lines);
    List<String> orderLines = csvReader.read(ORDER);
    List<String[]> result = parser.parse(orderLines, ";", 0);

    assertArrayEquals(new String[0], result.get(0));
    assertArrayEquals(new String[]{" "}, result.get(1));
    assertArrayEquals(new String[]{""}, result.get(2));
    assertArrayEquals(new String[]{"", "", "", "d"}, result.get(3));
  }

  @Test
  void testCsvLinesIsNull() {
    List<String[]> result = parser.parse(null, ";", 0);

    assertArrayEquals(new String[0], result.get(0));
  }

  @Test
  void testCsvParseWithCommaSeparator() {
    List<String> lines = Arrays.asList("a,b,", " ,b,c");

    Mockito.when(csvReader.read(ORDER)).thenReturn(lines);
    List<String> orderLines = csvReader.read(ORDER);
    List<String[]> result = parser.parse(orderLines, ",", 0);

    assertArrayEquals(new String[]{"a", "b"}, result.get(0));
    assertArrayEquals(new String[]{" ", "b", "c"}, result.get(1));
  }

  @Test
  void testCsvParseWithNullSeparator() {
    List<String> lines = Arrays.asList("a;b;", "c;");

    Mockito.when(csvReader.read(ORDER)).thenReturn(lines);
    List<String> orderLines = csvReader.read(ORDER);
    List<String[]> result = parser.parse(orderLines, null, 0);

    assertArrayEquals(new String[0], result.get(0));
  }

  @Test
  void testCsvParseScipLines() {
    List<String> lines = Arrays.asList("a;", "a;b;", "a;b;c;", "a;b;c;d;");

    Mockito.when(csvReader.read(ORDER)).thenReturn(lines);
    List<String> orderLines = csvReader.read(ORDER);
    List<String[]> result = parser.parse(orderLines, ";", 1);

    assertArrayEquals(new String[]{"a", "b"}, result.get(0));
    assertArrayEquals(new String[]{"a", "b", "c"}, result.get(1));
    assertArrayEquals(new String[]{"a", "b", "c", "d"}, result.get(2));

    assertThrows(IllegalArgumentException.class, () -> parser.parse(lines, ";", -1));
  }

}
