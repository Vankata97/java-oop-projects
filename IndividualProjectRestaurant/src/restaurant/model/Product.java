package restaurant.model;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {

  private String name;
  private BigDecimal price;
  private Category category;
  private Integer weight;

  public Product(String name, BigDecimal price, Category category, Integer weight) {
    this.name = name;
    this.price = price;
    this.category = category;
    this.weight = weight;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String productName) {
    this.name = productName;
  }

  public BigDecimal getPrice() {
    return this.price;
  }

  public void setPrice(BigDecimal productPrice) {
    this.price = productPrice;
  }


  @Override
  public boolean equals(Object object) {

    if (object instanceof Product) {
      Product product = (Product) object;
      if (product.getName() != null) {
        return product.getName().equals(this.getName());
      }
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
