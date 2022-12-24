package ua.hillelit.lms.myCSVUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.NoArgsConstructor;
import ua.hillelit.lms.entitys.Supermarket;

/**
 * {@link Utils} is a class for data transformation and calculations.
 *
 * @author Dmytro Trotsenko on 21.12.2022
 */
@NoArgsConstructor
public class Utils {

  /**
   * Convert list arrays of words {@code List<String[]>} to List<Supermarket>.
   *
   * @param wordsList the list separated by an array of words {@code List<String[]>}
   * @return list objects {@code List<Supermarket>}
   * @throws IllegalArgumentException when array words size not match 4
   */
  public static List<Supermarket> toSupermarketList(List<String[]> wordsList) {
    if (wordsList == null) {
      return Collections.singletonList(new Supermarket());
    }
    return wordsList.stream().map(data -> {
      if (data.length != 4) {
        throw new IllegalArgumentException(
            "The length of array words does not match number of fields in the object");
      }
      Supermarket supermarket = new Supermarket();
      supermarket.setName(data[0]);
      supermarket.setProductName(data[1]);
      supermarket.setPrice(Double.parseDouble(data[2]));
      supermarket.setQuantity(Integer.parseInt(data[3]));
      return supermarket;
    }).collect(Collectors.toList());
  }

  /**
   * Grouping {@code List<Supermarket>} by supermarket name.
   *
   * @param supermarkets list objects {@code List<Supermarket>}
   * @return Map supermarkets {@code Map<String, List<Supermarket>>}
   */
  public static Map<String, List<Supermarket>> groupingByName(List<Supermarket> supermarkets) {
    if (supermarkets == null) {
      return new HashMap<>();
    }
    return supermarkets.stream()
        .collect(Collectors.groupingBy(Supermarket::getName));
  }

  /**
   * Method for collect by product name, merge products, calculate the average price, total sum of
   * the quantity and convert to string.
   *
   * @param supermarkets list objects {@code List<Supermarket>}
   * @return merged and calculated product list {@code List<String>}
   */
  public static List<String> mergeProduct(List<Supermarket> supermarkets) {
    if (supermarkets == null) {
      return new ArrayList<>();
    }
    return supermarkets.stream()
        .collect(Collectors.toMap(Supermarket::getProductName
            , Function.identity(), Supermarket::averagePriceAndSumQuantity))
        .values().stream()
        .map(Supermarket::toString)
        .collect(Collectors.toList());
  }
}
