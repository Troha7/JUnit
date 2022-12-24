package ua.hillelit.lms;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import ua.hillelit.lms.myCSVUtils.Utils;
import ua.hillelit.lms.entitys.Supermarket;
import ua.hillelit.lms.myCSV.CSVParser;
import ua.hillelit.lms.myCSV.CSVReader;
import ua.hillelit.lms.myCSV.CSVWriter;

/**
 * Main class
 * @author Dmytro Trotsenko on 19.12.2022
 */
public class Main {

  private static final String ORDER_1 = "src/main/resources/order_11.csv";
  private static final String ORDER_2 = "src/main/resources/order_22.csv";
  private static final String ATB_RES = "src/main/resources/atb_res.csv";
  private static final String SILPO_RES = "src/main/resources/silpo_res.csv";
  private static final String TITLE = "НАИМЕНОВАНИЕ;ЦЕНА;ШТ;";

  public static void main(String[] args) {

    CSVReader csvReader = new CSVReader();
    CSVParser csvParser = new CSVParser();
    CSVWriter csvWriter = new CSVWriter();

    List<Supermarket> supermarkets = new ArrayList<>();

    List<String> order1Lines = csvReader.read(ORDER_1);
    List<String[]> order1Words = csvParser.parse(order1Lines,";",1);
    supermarkets.addAll(Utils.toSupermarketList(order1Words));

    List<String> order2Lines = csvReader.read(ORDER_2);
    List<String[]> order2Words = csvParser.parse(order2Lines,";",1);
    supermarkets.addAll(Utils.toSupermarketList(order2Words));

    Map<String,List<Supermarket>> supermarketMap = Utils.groupingByName(supermarkets);

    List<String> atbProductsLines = Utils.mergeProduct(supermarketMap.get("АТБ"));
    atbProductsLines.add(0,TITLE);

    List<String> silpoProductsLines = Utils.mergeProduct(supermarketMap.get("Сильпо"));
    silpoProductsLines.add(0,TITLE);

    csvWriter.write(ATB_RES,atbProductsLines);
    csvWriter.write(SILPO_RES,silpoProductsLines);
  }

}
