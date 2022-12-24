package ua.hillelit.lms.entitys;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * {@link Supermarket} is a supermarket data class.
 *
 * @author Dmytro Trotsenko on 20.12.2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Supermarket {

  private String name;
  private String productName;
  private double price;
  private int quantity;

  //This method for merge products and calculate the average price and total sum of the quantity
  public static Supermarket averagePriceAndSumQuantity(Supermarket first, Supermarket second) {
    first.setPrice((first.getPrice() + second.getPrice()) / 2);
    first.setQuantity(first.getQuantity() + second.getQuantity());
    return first;
  }

  //Create string for .csv file
  @Override
  public String toString() {
    return productName + ";" + price + ";" + quantity + ";";
  }
}
