package restaurant;


import static java.lang.System.exit;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import restaurant.model.Client;
import restaurant.model.Employee;
import restaurant.model.JobTitle;
import restaurant.model.Menu;
import restaurant.model.Order;
import restaurant.model.Product;
import restaurant.model.Restaurant;
import restaurant.model.Table;
import restaurant.util.CommonUtil;


public class Main {

  private static Restaurant restaurant;

  public static void main(String[] args) {
    /* Database */
    restaurant = new Restaurant();

    fillRestaurant();
    fillWarehouse();
    createEmployees();

    // Business Logic

    //TODO:Make to run the tables at least 3 times per day (depending on the clients) and get the weekly update (or monthly update)
    for (int i = 0; i < 7; i++) {
      System.out.println();
      System.out.println("---------- DAY " + (i + 1));
      System.out.println();

      List<Client> clients = getClients();
      while (clients.size() > 0 && (restaurant.hasFreeTable() || restaurant.hasFreeBarSeats())) {
        Integer clientsCount = CommonUtil.getRandomInteger(1, Math.min(clients.size(), 4));
        List<Client> group = getGroupOfClients(clients, clientsCount);

        clients.removeAll(group);

        if (restaurant.hasFreeTable()) {
          Table freeTable = tableAccommodationProcess(group);
          tableOrderProcess(group, freeTable);
        } else {
          barOrderProcess(group);
        }
      }
      System.out.println();
      paymentProcess();
      paySalaries();
      printStatisticsForCurrentDay();

      restaurant.getEmployees().forEach(e -> e.setTips(BigDecimal.ZERO));

      for (Table table : restaurant.getTables()) {
        table.setUsedSeats(0);
      }

      restaurant.getBar().setUsedSeats(0);

      restaurant.setOrders(new ArrayList<>());
    }

    System.out.println();
    System.out.println("СЕДМИЧНА СТАТИСТИКА");
    restaurant.getEmployees().forEach(e -> {
      System.out.println(e.getName() + ": Седмична заплата " + e.getWeeklyIncome()
          .setScale(2, RoundingMode.HALF_UP) + "лв.");
    });

    System.out.println();

    System.out.println("ПЕЧАЛБА НА РЕСТОРАНТА ЗА ИЗМИНАЛАТА СЕДМИЦА "
        + restaurant.getAmount().subtract(BigDecimal.valueOf(1000))
        .setScale(2, RoundingMode.HALF_UP)
        + "лв.");

    System.out.println();
  }

  private static void paySalaries() {
    for (Employee employee : restaurant.getEmployees()) {
      employee.setWeeklyIncome(
          employee.getWeeklyIncome()
              .add(employee.getDailySalary())
              .add(employee.getTips())
      );

      BigDecimal restaurantAmount = restaurant.getAmount();
      restaurant.setAmount(restaurantAmount.subtract(employee.getDailySalary()));

      if (restaurant.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
        System.out.println("Nema parichki");
        exit(0);
      }

      // Ако служителят е получил башкиш над 100 лева му се увеличава дневното възнаграждение с 2%
      if (employee.getTips().compareTo(BigDecimal.valueOf(100)) >= 0) {
        employee.setDailySalary(
            employee.getDailySalary().multiply(BigDecimal.valueOf(1.02))
        );
      }
    }
  }

  private static void barOrderProcess(List<Client> group) {
    Order order = new Order();
    order.setBar(true);

    restaurant.getBar().setUsedSeats(
        restaurant.getBar().getUsedSeats() + group.size()
    );

    for (int i = 0; i < group.size(); i++) {
      //TODO: set to order only drinks
      Map<Product, Integer> randomProductFromMenu = getRandomProductFromMenu(2);
      for (Map.Entry<Product, Integer> entry : randomProductFromMenu.entrySet()) {
        order.addToOrder(entry.getKey().getName(), entry.getValue());
        removeProductQuantityFromWarehouse(entry.getKey(), entry.getValue());
      }
    }

    restaurant.getOrders().add(order);
  }

  private static List<Client> getGroupOfClients(List<Client> clients, Integer clientsCount) {
    List<Client> group = new ArrayList<>();
    for (int i = 0; i < clientsCount; i++) {
      if (clients.size() >= 4) {
        group.add(clients.get(i));
      } else {
        group.add(clients.get(0));
      }
    }
    return group;
  }

  private static void printStatisticsForCurrentDay() {
    System.out.println(
        "Оборот: " + restaurant.getAmount().setScale(2, RoundingMode.HALF_UP) + "лв.");

    System.out.println("Служители с бакшиши: ");
    restaurant.getEmployees()
        .forEach(e -> {
          System.out.println("- Служител: " + e.getName() + "     Бакшиш: " + e.getTips() + "лв.");
        });

    Map<String, Integer> ordersStats = new HashMap<>(); // <PRODUCT_NAME, ORDERED_COUNT>
    for (Order order : restaurant.getOrders()) {
      for (Map.Entry<String, Integer> entrySet : order.getOrderDetails().entrySet()) {
        String productName = entrySet.getKey();
        Integer productQuantity = entrySet.getValue();

        // Добавяме продукта, ако го няма в списъка
        ordersStats.putIfAbsent(productName, 0);
        ordersStats.put(productName, ordersStats.get(productName) + productQuantity);
      }
    }

    System.out.println();

    // Сортираме данните Asc
    ordersStats = ordersStats.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1,
            LinkedHashMap::new));

    // Намираме най-малко продаваният продукт за деня
    String worstProduct = ordersStats.entrySet()
        .stream()
        .findFirst()
        .orElseThrow(RuntimeException::new)
        .getKey();

    // Премахваме най-малко продаваният продукт за деня от менюто
    restaurant.getMenu().stopSell(worstProduct);
    System.out.println("Най-малко продаваният продукт е " + worstProduct);

    // Сортираме данните Desc
    ordersStats = ordersStats.entrySet()
        .stream()
        .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1,
            LinkedHashMap::new));

    // Намираме най-продавният продукт за деня
    String bestSeller = ordersStats.entrySet()
        .stream()
        .findFirst()
        .orElseThrow(RuntimeException::new)
        .getKey();

    restaurant.getMenu().changeProductPriceByProductName(bestSeller);

    System.out.println("Най-продаваният продукт за деня: " + bestSeller);
  }

  private static void paymentProcess() {
    for (Order order : restaurant.getOrders()) {

      Map<String, Integer> details = order.getOrderDetails();
      for (Map.Entry<String, Integer> entrySet : details.entrySet()) {
        String productName = entrySet.getKey();
        Integer productQuantity = entrySet.getValue();

        // Това ще го вземем от менюто
        BigDecimal productPrice = restaurant.getMenu().getProductPrice(productName);

        BigDecimal totalPrice = productPrice.multiply(BigDecimal.valueOf(productQuantity));
        restaurant.setAmount(restaurant.getAmount().add(totalPrice));

        // if num == 1 -> has tips
        if (CommonUtil.getRandomInteger(1, 2) == 1) {
          BigDecimal randomTip = CommonUtil.getRandomBigDecimal(1, 10);

          if (order.getTableNumber() != null) {
            Integer tableNumber = order.getTableNumber();
            Table table = restaurant.getTableByNumber(tableNumber);
            Employee currentWaiter = table.getCurrentWaiter();
            currentWaiter.giveTip(randomTip);
          } else {
            restaurant.getBar().getCurrentBartender().giveTip(randomTip);
          }

        }
      }
    }
  }

  private static void tableOrderProcess(List<Client> group, Table freeTable) {
    Order order = new Order(freeTable.getNumber());
    for (int i = 0; i < group.size(); i++) {
      Map<Product, Integer> randomProductFromMenu = getRandomProductFromMenu(2);
      for (Map.Entry<Product, Integer> entry : randomProductFromMenu.entrySet()) {
        order.addToOrder(entry.getKey().getName(), entry.getValue());
        removeProductQuantityFromWarehouse(entry.getKey(), entry.getValue());
      }
    }
    restaurant.getOrders().add(order);
  }

  /**
   * Връща рандом продукт и рандом количество за него
   */
  private static Map<Product, Integer> getRandomProductFromMenu(Integer productCount) {
    Map<Product, Integer> productWithQuantity = new HashMap<>();
    for (int i = 0; i < productCount; i++) {
      Product product = restaurant.getMenu().getAllProducts()
          .get(CommonUtil.getRandomInteger(0, restaurant.getMenu().getAllProducts().size()));
      Integer productQuantity = CommonUtil.getRandomInteger(1, 3);
      productWithQuantity.put(product, productQuantity);
    }
    return productWithQuantity;
  }

  private static void removeProductQuantityFromWarehouse(Product product, Integer productQuantity) {

    if (restaurant.getWarehouse().getProducts().get(product) > productQuantity) {
      restaurant.getWarehouse().removeProductQuantity(product, productQuantity);
    } else {
      restaurant.getWarehouse().fillWithQuantity(product, CommonUtil.getRandomInteger(10, 20));
    }

  }

  private static Table tableAccommodationProcess(List<Client> group) {
    Table freeTable = restaurant.getFreeTable();
    Employee randomWaiter = restaurant.getRandomWaiter();
    freeTable.setCurrentWaiter(randomWaiter);
    freeTable.setUsedSeats(group.size());
    return freeTable;
  }

  private static void fillRestaurant() {
    List<Table> tables = new ArrayList<>();

    Integer integer = CommonUtil.getRandomInteger(5, 16);
    for (int i = 0; i < integer; i++) {
      Table table = new Table(i + 1, 4);
      tables.add(table);
    }

    restaurant.setTables(tables);
  }

  /**
   * Създава между 1 и 20 клиента
   *
   * @return Collection от Client
   */
  private static List<Client> getClients() {
    List<Client> clients = new ArrayList<>();

    Integer randomInteger = CommonUtil.getRandomInteger(15, 50);
    for (int i = 0; i < randomInteger; i++) {
      Client client = new Client("Client_" + i);
      clients.add(client);
    }

    return clients;
  }

  private static void createEmployees() {
    List<String> employeesData = null;
    try {
      employeesData = Files.readAllLines(Paths.get(CommonUtil.DATA_PATH + CommonUtil.EMPLOYEES_FILE_NAME));
    } catch (IOException e) {
      throw new IllegalArgumentException();
    }

    for (String employeeData : employeesData) {
      String[] data = employeeData.split("\\|");

      // Това ще преобразува името на категорията, така че да може да го намерим е енумерацията
      JobTitle jobTitle = JobTitle.valueOf(CommonUtil.formatString(data[0]));
      String employeeName = data[1];
      BigDecimal employeeSalary = new BigDecimal(data[2]);

      Employee employee = new Employee(employeeName, employeeSalary, jobTitle);
      restaurant.getEmployees().add(employee);

      if (employee.getJobTitle().equals(JobTitle.BARTENDER)) {
        restaurant.getBar().setCurrentBartender(employee);
      }
    }
  }

  private static void fillWarehouse() {
    Menu menu = restaurant.getMenu();
    for (Product product : menu.getAllProducts()) {
      restaurant.getWarehouse().addProduct(product, CommonUtil.getRandomInteger(1, 15));
    }
  }

}
