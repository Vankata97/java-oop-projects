package staff;

import clients.Client;
import clients.Order;
import interfaces.Billable;
import restaurant.Management;
import restaurant.Menu;
import restaurant.Restaurant;
import restaurant.Table;
import storage.Item;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

public class Waiter extends Staff implements Billable
{

  private BigDecimal   moneyFromClients;
  private BigDecimal   tipFromClients;
  private Queue<Order> orders;
  private List<Table>  tables;

  public Waiter(String name, BigDecimal salary)
  {
    super(name, salary);
    moneyFromClients = BigDecimal.ZERO;
    tipFromClients = BigDecimal.ZERO;
    tables = new ArrayList<>();
    orders = new LinkedList<>();
  }

  @Override
  public void addBillToRestaurant(BigDecimal moneyFromClients)
  {
    Management.setInitialAmount(Management
        .getInitialAmount()
        .add(moneyFromClients));
  }

  public void addOrdersToWaiter(Cheff cheff, Bartender bartender)
  {
    Menu menu = new Menu();
    for (Table table : tables) {
      if (!table.isOrderTaken() && table.isTableBusy() && table.getClients() != null) {
        List<Client> clients = table.getClients();
        for (Client client : clients) {
          client.generateOrder(menu);

          this.orders.add(client.getOrder());
          distributeOrders(cheff, bartender);
        }
        table.getTableBill();
        table.getTableTip();
        table.setOrderTaken(true);
        table.setTableBusy(false);
        table.setClients(new ArrayList<>());
        orders = new LinkedList<>();
      }
    }
    takeBillsFromTable();
    takeTipFromTable();
  }

  public Order makeFoodOrder()
  {

    Order foodOrder = new Order();
    for (Item item : orders
        .peek()
        .getItems()) {
      if (item
          .getCategory()
          .equals("food")) {
        foodOrder.addItemToOrder(item);
//        foodOrder.setOrderPrice(item.getPrice());
      }
    }
    return foodOrder;
  }

  public Order makeDrinkOrder()
  {
    Order drinkOrder = new Order();
    for (Item item : orders
        .peek()
        .getItems()) {
      if (item
          .getCategory()
          .equals("drink")) {
        drinkOrder.addItemToOrder(item);
//        drinkOrder.setOrderPrice(item.getPrice());
      }
    }
    return drinkOrder;
  }

  public void giveOrderToCheff(Cheff cheff)
  {
    cheff.setOrders(makeFoodOrder());
  }

  public void giveOrderToBartender(Bartender bartender)
  {

    bartender.setOrders(makeDrinkOrder());
  }

  public void distributeOrders(Cheff cheff, Bartender bartender)
  {
    while (!orders.isEmpty()) {
      giveOrderToCheff(cheff);
      giveOrderToBartender(bartender);
      orders.poll();
    }
  }

  public void takeBillsFromTable()
  {
    for (Table table : tables) {
      this.moneyFromClients = this.moneyFromClients.add(table
          .getTableBill()
          .setScale(2, RoundingMode.HALF_UP));
    }
  }

  public void takeTipFromTable()
  {
    for (Table table : tables) {
      this.tipFromClients = this.tipFromClients.add(table
          .getTableTip()
          .setScale(2, RoundingMode.HALF_UP));
    }
  }

  public void addTable(Table table)
  {
    this.tables.add(table);
  }

  public BigDecimal getMoneyFromClients()
  {
    return moneyFromClients;
  }

  public BigDecimal getTipFromClients()
  {
    return tipFromClients;
  }

  @Override
  public String toString()
  {
    return "staff.Waiter{" +
        "orders=" + orders +
        '}';
  }

}
