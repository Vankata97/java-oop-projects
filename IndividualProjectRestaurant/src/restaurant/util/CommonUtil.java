package restaurant.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Locale;

public class CommonUtil {

  public static final String DATA_PATH = "IndividualProjectRestaurant/src/restaurant/data/";
  public static final String PRODUCTS_FILE_NAME = "Products.txt";
  public static final String EMPLOYEES_FILE_NAME = "Employees.txt";

  public static BigDecimal getRandomBigDecimal(int min, int max) {
    return BigDecimal.valueOf((Math.random() * (max - min)) + min)
        .setScale(2, RoundingMode.HALF_UP);
  }

  public static Integer getRandomInteger(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  public static String formatString(String string) {
    String s = string.replaceAll(" ", "_");
    s = s.toUpperCase(Locale.ROOT);
    return s;
  }
}
