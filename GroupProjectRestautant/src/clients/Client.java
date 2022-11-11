package clients;

import restaurant.Menu;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Random;

public class Client {

  private boolean isAdult;
  private boolean isOnTheTable;
  private Order order;
  private BigDecimal clientBill;
  private BigDecimal clientTip;

  public Client(boolean isAdult) {
    this.isAdult = isAdult;
    this.isOnTheTable = false;
    this.clientBill = new BigDecimal("0");
    this.clientTip =new BigDecimal("0");
    order = new Order();
  }

  public void generateOrder(Menu menu) {
    Random rm = new Random();

    int randomSaladIndex = rm.nextInt(menu.getSalads().size());
    this.order.addItemToOrder(menu.getSalads().get(randomSaladIndex));

    int randomMainDish = rm.nextInt(menu.getMainDishes().size());
    this.order.addItemToOrder(menu.getMainDishes().get(randomMainDish));

    int randomDessert = rm.nextInt(menu.getDesserts().size());
    this.order.addItemToOrder(menu.getDesserts().get(randomDessert));

    if (!isAdult) {
      menu.setAlcoholicDrinks(null);
    }
    if (menu.getAlcoholicDrinks() != null) {
      int randomAlcoholicDrink = rm.nextInt(menu.getAlcoholicDrinks().size());
      this.order.addItemToOrder(menu.getAlcoholicDrinks().get(randomAlcoholicDrink));
    }
    int randomSoftDrink = rm.nextInt(menu.getSoftDrinks().size());
    this.order.addItemToOrder(menu.getSoftDrinks().get(randomSoftDrink));

    calculateClientBill();
    calculateClientTip();
  }

  public void generateBarOrder(Menu menu) {
    Random rm = new Random();
    if (!isAdult) {
      menu.setAlcoholicDrinks(null);
    }
    if (menu.getAlcoholicDrinks() != null) {
      int randomAlcoholicDrink = rm.nextInt(menu.getAlcoholicDrinks().size());
      this.order.addItemToOrder(menu.getAlcoholicDrinks().get(randomAlcoholicDrink));
    }
    int randomSoftDrink = rm.nextInt(menu.getSoftDrinks().size());
    this.order.addItemToOrder(menu.getSoftDrinks().get(randomSoftDrink));

    calculateClientBill();
    calculateClientTip();
  }

  public void calculateClientTip() {
    Random random = new Random();
    int num = random.nextInt(10) + 1;
    if (num > 5) {
      this.clientTip = this.clientBill.divide(new BigDecimal("10"), 2, RoundingMode.HALF_UP);
    }
  }

  public void calculateClientBill() {
    for (int i = 0; i < order.getItems().size(); i++) {
      this.clientBill = this.clientBill.add(order.getItems().get(i).getPrice())
          .setScale(2, RoundingMode.HALF_UP);
    }
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public Order getOrder() {
    return order;
  }

  public boolean isOnTheTable() {
    return isOnTheTable;
  }

  public void setOnTheTable(boolean onTheTable) {
    isOnTheTable = onTheTable;
  }

  public BigDecimal getClientTip() {
    return clientTip;
  }

  public BigDecimal getClientBill() {
    return clientBill;
  }

  @Override
  public String toString() {
    return "clients.Client{" +
        "isAdult=" + isAdult +
        "bill=" + clientBill +
        "tip=" + clientTip +
        "isOnTheTable =" + isOnTheTable +
        ", order=" + order +
        '}';
  }
}
