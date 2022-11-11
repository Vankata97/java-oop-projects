package restaurant.model;

import java.util.HashMap;
import java.util.Map;

public class Warehouse {

  private final Map<Product, Integer> products; // <PRODUCT, QUANTITY>

  public Warehouse() {
    this.products = new HashMap<>();
  }

  public Product getProduct(String productName) {
    for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
      Product product = entry.getKey();
      String name = product.getName();

      if (name.equals(productName)) {
        return product;
      }
    }

    throw new RuntimeException("Product not found!");
  }

  public void addProduct(Product product, Integer quantity) {
    this.products.put(product, quantity);
  }

  public void removeProduct(Product product, Integer quantity) {
    this.products.remove(product, quantity);
  }

  public Map<Product, Integer> getProducts() {
    return products;
  }

  public void fillWithQuantity(Product product, Integer quantity) {
    this.products.put(product, quantity);
  }

  /**
   * Изписва бройки от склада за даден продукт
   *
   * @param product          Продукт
   * @param quantityToRemove Количество, което да бъде премахнато от склада
   */
  public void removeProductQuantity(Product product, Integer quantityToRemove) {
    Integer currentProductQuantity = this.products.get(product);
    Integer totalQuantity = currentProductQuantity - quantityToRemove;
    this.products.put(product, totalQuantity);
  }
}
