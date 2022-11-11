package staff;

import clients.Order;
import java.io.FileNotFoundException;
import restaurant.Management;
import storage.Storage;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Cheff extends Staff {

  private Queue<Order> orders;

  public Cheff(String name, BigDecimal salary) {
    super(name, salary);
    orders = new LinkedList<>();
  }

  public void setOrders(Order order) {
    this.orders.add(order);
  }

  public void removeFoodsFromStorage(Storage storage) throws FileNotFoundException
  {
    Order order;
    while (!orders.isEmpty()) {
      order = orders.poll();
      for (int i = 0; i < order.getItems().size(); i++) {
        String item = order.getItems().get(i).getName();
        storage.decreaseFoodValue(item);

        Management.removeInitialAmount(order
            .getItems()
            .get(i)
            .getPrice()
            .multiply(new BigDecimal("0.3")));
      }
    }
  }



  @Override
  public String toString() {
    return "staff.Cheff{" +
        "orders=" + orders +
        '}';
  }
}
