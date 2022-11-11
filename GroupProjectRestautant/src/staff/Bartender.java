package staff;

import clients.Client;
import clients.Order;
import interfaces.Billable;
import java.io.FileNotFoundException;
import restaurant.Bar;
import restaurant.Management;
import restaurant.Menu;
import restaurant.Restaurant;
import storage.Storage;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

public class Bartender extends Staff implements Billable
{

  private Queue<Order> orders;
  private BigDecimal   moneyFromClients;
  private BigDecimal   tipFromClients;

  public Bartender(String name, BigDecimal salary)
  {
    super(name, salary);
    orders = new LinkedList<>();
    this.moneyFromClients = new BigDecimal("0");
    this.tipFromClients = new BigDecimal("0");
  }

  @Override
  public void addBillToRestaurant(BigDecimal moneyFromClients)
  {
    Management.setInitialAmount(Management
        .getInitialAmount()
        .add(moneyFromClients));
  }

  public void removeDrinksFromStorage(Storage storage)
  {
    Order order;
    while (!orders.isEmpty()) {
      order = orders.poll();
      for (int i = 0; i < order
          .getItems()
          .size(); i++) {
        try {
          storage.decreaseDrinkValue(order
              .getItems()
              .get(i)
              .getName());

          Management.removeInitialAmount(order
              .getItems()
              .get(i)
              .getPrice()
              .multiply(new BigDecimal("0.5")));
        }
        catch (FileNotFoundException e) {
          throw new RuntimeException(e);
        }
      }
    }
  }

  public void takeOrdersFromBar(Bar bar)
  {
    Menu menu = new Menu();
    for (Client client : bar.getBarClients()) {
      client.setOrder(new Order());
      client.generateBarOrder(menu);
      orders.add(client.getOrder());

    }
    calculateBillFromBar(bar);
    calculateTipFromBar(bar);
    bar.setBusy(false);
    bar.setBarClients(new ArrayList<>());
  }

  public BigDecimal getMoneyFromClients()
  {
    return moneyFromClients;
  }

  public void setOrders(Order order)
  {
    this.orders.add(order);
  }

  private void calculateBillFromBar(Bar bar)
  {
    for (int i = 0; i < bar
        .getBarClients()
        .size(); i++) {
      this.moneyFromClients = this.moneyFromClients
          .add(bar
              .getBarClients()
              .get(i)
              .getClientBill())
          .setScale(2, RoundingMode.HALF_UP);
    }
  }

  private void calculateTipFromBar(Bar bar)
  {
    for (int i = 0; i < bar
        .getBarClients()
        .size(); i++) {
      this.tipFromClients = this.tipFromClients
          .add(bar
              .getBarClients()
              .get(i)
              .getClientTip())
          .setScale(2, RoundingMode.HALF_UP);
    }
  }


  @Override
  public BigDecimal getTipFromClients()
  {
    return tipFromClients;
  }

  @Override
  public String toString()
  {
    return "staff.Bartender{" +
        "orders=" + orders +
        '}';
  }
}

