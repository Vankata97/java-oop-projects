package clients;

import java.math.BigDecimal;
import storage.Item;

import java.util.ArrayList;
import java.util.List;

public class Order {

  private List<Item> items;

  public Order() {
    items = new ArrayList<>();
  }

  public void addItemToOrder(Item item) {
    items.add(item);
  }

  public List<Item> getItems() {
    return items;
  }

  @Override
  public String toString() {
    return "clients.Order{" +
        "items=" + items +
        '}';
  }
}
