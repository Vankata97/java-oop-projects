package restaurant.model;

import java.util.stream.Collectors;
import restaurant.util.CommonUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Restaurant {

  private static final String RESTAURANT_NAME = "Hideout";
  private static final BigDecimal START_AMOUNT = BigDecimal.valueOf(1000);

  private String name;
  private BigDecimal amount;
  private final Warehouse warehouse;
  private List<Employee> employees;
  private List<Table> tables;
  private Menu menu;
  private List<Order> orders;
  private Bar bar;

  public Restaurant() {
    setName(RESTAURANT_NAME);
    setAmount(START_AMOUNT);

    this.warehouse = new Warehouse();
    this.employees = new ArrayList<>();
    this.tables = new ArrayList<>();
    this.menu = new Menu();
    this.orders = new ArrayList<>();
    this.bar = new Bar(10);
  }

  public Bar getBar() {
    return bar;
  }

  public void setBar(Bar bar) {
    this.bar = bar;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public Warehouse getWarehouse() {
    return warehouse;
  }

  public List<Employee> getEmployees() {
    return employees;
  }

  public List<Table> getTables() {
    return tables;
  }

  public void setTables(List<Table> tables) {
    this.tables = tables;
  }

  public Menu getMenu() {
    return menu;
  }

  public List<Order> getOrders() {
    return orders;
  }

  public void setOrders(List<Order> orders) {
    this.orders = orders;
  }

  public boolean hasFreeTable() {
    Table freeTable = this.getTables()
        .stream()
        .filter(table -> table.getUsedSeats().compareTo(0) == 0)
        .findFirst()
        .orElse(null);

    return freeTable != null;
  }

  public boolean hasFreeBarSeats() {
    return this.getBar().getUsedSeats().compareTo(this.getBar().getSeats()) <= 0;
  }

  public Table getFreeTable() {
    return this.getTables()
        .stream()
        .filter(table -> table.getUsedSeats().compareTo(0) == 0)
        .findFirst()
        .orElse(null);
  }

  public Table getTableByNumber(Integer tableNumber) {
    return this.getTables().stream()
        .filter(table -> table.getNumber().equals(tableNumber))
        .collect(Collectors.toList())
        .get(0);
  }

  public Employee getRandomWaiter() {
    List<Employee> waiters = new ArrayList<>();
    for (Employee employee : this.employees) {
      if (employee.getJobTitle().equals(JobTitle.WAITER)) {
        waiters.add(employee);
      }
    }

    Integer randomNumber = CommonUtil.getRandomInteger(0, waiters.size());
    return waiters.get(randomNumber);
  }

  @Override
  public String toString() {
    return "Restaurant{" +
        "name='" + name + '\'' +
        ", amount=" + amount +
        '}';
  }
}
