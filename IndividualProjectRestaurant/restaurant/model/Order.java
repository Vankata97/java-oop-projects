package restaurant.model;

import java.util.HashMap;
import java.util.Map;

public class Order {

  private Map<String, Integer> orderDetails; // <PRODUCT_NAME, PRODUCT_QUANTITY>
  private Integer tableNumber;
  private Boolean isBar;

  public Order() {
    this.tableNumber = null;
    this.orderDetails = new HashMap<>();
  }

  public Order(Integer tableNumber) {
    this();
    this.tableNumber = tableNumber;
  }

  public Boolean getBar() {
    return isBar;
  }

  public void setBar(Boolean bar) {
    isBar = bar;
  }

  public void addToOrder(String productName, Integer quantity) {
    this.orderDetails.put(productName, quantity);
  }

  public Integer getTableNumber() {
    return tableNumber;
  }

  public void setTableNumber(Integer tableNumber) {
    this.tableNumber = tableNumber;
  }

  public Map<String, Integer> getOrderDetails() {
    return orderDetails;
  }

  public void setOrderDetails(Map<String, Integer> orderDetails) {
    this.orderDetails = orderDetails;
  }
}
