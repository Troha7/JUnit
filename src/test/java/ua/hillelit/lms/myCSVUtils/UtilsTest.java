package ua.hillelit.lms.myCSVUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ua.hillelit.lms.entitys.Supermarket;
import ua.hillelit.lms.myCSV.CSVParser;
import ua.hillelit.lms.myCSV.CSVReader;


/**
 * Test class for {@link Utils}.
 *
 * @author Dmytro Trotsenko on 22.12.2022
 */
public class UtilsTest {

  private final String ORDER = "src/main/resources/order_11.csv";
  CSVReader csvReader = Mockito.mock(CSVReader.class);
  CSVParser csvParser = Mockito.mock(CSVParser.class);

  @Test
  void testToObjectList() {
    List<String[]> testList = Arrays.asList(new String[]{"АТБ", "Гречка", "31.25", "120"},
        new String[]{"Сильпо", "Сахар", "21.40", "107"});

    Mockito.when(csvParser.parse(csvReader.read(ORDER), ";", 0)).thenReturn(testList);
    List<String[]> orderWords = csvParser.parse(csvReader.read(ORDER), ";", 0);
    List<Supermarket> supermarkets = Utils.toSupermarketList(orderWords);

    assertEquals("АТБ", supermarkets.get(0).getName());
    assertEquals("Гречка", supermarkets.get(0).getProductName());
    assertEquals(31.25, supermarkets.get(0).getPrice());
    assertEquals(120, supermarkets.get(0).getQuantity());

    assertEquals("Сильпо", supermarkets.get(1).getName());
    assertEquals("Сахар", supermarkets.get(1).getProductName());
    assertEquals(21.40, supermarkets.get(1).getPrice());
    assertEquals(107, supermarkets.get(1).getQuantity());
  }

  @Test
  void testToObjectListIsNull() {
    List<Supermarket> supermarkets = Utils.toSupermarketList(null);

    assertNull(supermarkets.get(0).getName());
    assertNull(supermarkets.get(0).getProductName());
    assertEquals(0, supermarkets.get(0).getPrice());
    assertEquals(0, supermarkets.get(0).getQuantity());
  }

  @Test
  void testToObjectListMatchesNumFields() {
    List<String[]> testList = new ArrayList<>();
    testList.add(new String[]{"АТБ"});

    Mockito.when(csvParser.parse(csvReader.read(ORDER), ";", 0)).thenReturn(testList);
    List<String[]> orderWords = csvParser.parse(csvReader.read(ORDER), ";", 0);

    assertThrows(IllegalArgumentException.class, () -> Utils.toSupermarketList(orderWords));
  }

  @Test
  void testGroupingByName() {
    List<Supermarket> supermarkets = Arrays.asList(new Supermarket("АТБ", "Гречка", 31.25, 120),
        new Supermarket("Сильпо", "Сахар", 21.40, 107));

    Map<String, List<Supermarket>> supermarketMap = Utils.groupingByName(supermarkets);

    assertEquals("АТБ", supermarketMap.get("АТБ").get(0).getName());
    assertEquals("Гречка", supermarketMap.get("АТБ").get(0).getProductName());

    assertEquals("Сильпо", supermarketMap.get("Сильпо").get(0).getName());
    assertEquals("Сахар", supermarketMap.get("Сильпо").get(0).getProductName());
  }

  @Test
  void testGroupingByNameIsNull() {
    assertNotNull(Utils.groupingByName(null));
  }

  @Test
  void testMergeProduct() {
    List<Supermarket> supermarkets = Arrays.asList(new Supermarket("АТБ", "Гречка", 100.0, 10),
        new Supermarket("АТБ", "Гречка", 80.5, 5));

    List<String> mergedProducts = Utils.mergeProduct(supermarkets);

    assertEquals("Гречка;90.25;15;", mergedProducts.get(0));
  }

  @Test
  void testMergeProductIsNull() {
    assertNotNull(Utils.mergeProduct(null));
  }
}
