package storage;

import java.math.BigDecimal;

public class Item {

  private String name;
  private String category;
  private BigDecimal price;
  private double grams;


  public Item(String name, String category, double grams, BigDecimal price) {
    this.name = name;
    this.category = category;
    this.grams = grams;
    this.price = price;
  }

  public String getName() {
    return name;
  }

  public String getCategory() {
    return category;
  }

  public BigDecimal getPrice() {
    return price;
  }

  @Override
  public String toString() {
    return '\n' + name + ", " +
        price + " лв, " +
        grams + " кг/л";
  }
}
