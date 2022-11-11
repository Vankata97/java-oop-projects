# FIRST TEAM PROJECT - RESTAURANT

The following repo contains information about the project

## GUIDELINES:
1.Clone this repository
2.Go to team-3 directory and start Main.java
3.Once the java app starts, it will show you the result of simulation of a day in our restaurant :)

##Source Code Review
Source code for the restaurant is located in /team-3/src/Main.java. 

We are generating the restaurant employees with the next method:
```java
private void staffGenerator() {

    staff.add(new Waiter("Георги", BigDecimal.valueOf(1350)));
    staff.add(new Waiter("Мария", BigDecimal.valueOf(1250)));
    staff.add(new Cheff("Петър", BigDecimal.valueOf(3000)));
    staff.add(new Bartender("Димитър", BigDecimal.valueOf(1500)));
    staff.add(new Hostess("Виктория", BigDecimal.valueOf(2000)));
  }
```
After that we continue with the generation of clients
```java
private void clientGenerator() {
    Random random = new Random();
    int clientsPerDay = random.nextInt(20, 31);
    System.out.println("Брой клиенти за деня: " + clientsPerDay);
    boolean isAdult;
    Client client;
    for (int i = 1; i <= clientsPerDay; i++) {
      isAdult = true;
      if (i % 5 == 0) {
        isAdult = false;
      }
      client = new Client(isAdult);
      clients.add(client);
    }
  }
```

With the next methods we are separating the clients into available tables and into the available seats on the bar
```java
public void separateClientsIntoTables(List<Client> clients, List<Table> tables) {
    Random random = new Random();
    int clientsPerTable;

    for (Table table : tables) {
      clientsPerTable = random.nextInt(2, 5);

      if (clients.size() < clientsPerTable) {
        clientsPerTable = clients.size();
      }

      System.out.println("Брой клиенти на маса " + table.getTableNumber() + ": " + clientsPerTable);

      if (clients.size() != 0) {
        if (!table.isTableBusy()) {
          for (int j = clientsPerTable - 1; j >= 0; j--) {
            if (!clients.get(j).isOnTheTable()) {
              table.addClientToTable(clients.get(j));
              clients.get(j).setOnTheTable(true);
              clients.remove(clients.get(j));
            }
          }

          table.setTableBusy(true);
          table.setOrderTaken(false);
        }
      }
    }
  }

public void separateClients(List<Client> clients, List<Table> tables, Bar bar) {
    separateClientsIntoTables(clients, tables);
    int remainClients = clients.size();

    if (remainClients > bar.getBarCapacity()) {
      remainClients = bar.getBarCapacity();
    }
    System.out.println("Брой клиенти на бара: " + remainClients);

    for (int i = remainClients - 1; i >= 0; i--) {
      if (clients.size() != 0) {
        bar.addClient(clients.get(i));
      }
    }

    if (remainClients == bar.getBarCapacity()) {
      bar.setBusy(true);
    }

    if (clients.size() > 0) {
      System.out.println("Брой чакащи клиенти за маса: " + clients.size());
      System.out.println("Моля изчакайте !!!");
      System.out.println();
    }
  }
```
And after that we are generating the orders
```java
 public Order makeFoodOrder() {

    Order foodOrder = new Order();
    for (Item item : orders.peek().getItems()) {
      if (item.getCategory().equals("food")) {
        foodOrder.addItemToOrder(item);
      }
    }
    return foodOrder;
  }

  public Order makeDrinkOrder() {
    Order drinkOrder = new Order();
    for (Item item : orders.peek().getItems()) {
      if (item.getCategory().equals("drink")) {
        drinkOrder.addItemToOrder(item);
      }
    }
    return drinkOrder;
  }
```

Everytime when a product is ordered we are calling the next methods to decrease the food quantity in from the storage
and if they are less than 1, we are restoring the quantity
```java
public void decreaseFoodValue(String str) {
    if (foods.containsKey(str)) {
      if (foods.get(str) > 1) {
        foods.put(str, foods.get(str) - 1);
        increaseNumberOfProductsStatistic(str);

      } else if (foods.get(str) == 1) {

        foods.put(str, foods.get(str) - 1);
        increaseNumberOfProductsStatistic(str);
        foods.put(str, foods.get(str) + 10);
        System.out.println("Зареждане на храни: " + str);

      }
    }
  }

  public void decreaseDrinkValue(String str) {
    if (drinks.containsKey(str)) {
      if (drinks.get(str) > 1) {
        drinks.put(str, drinks.get(str) - 1);
        increaseNumberOfProductsStatistic(str);

      } else if (drinks.get(str) == 1) {

        drinks.put(str, drinks.get(str) - 1);
        increaseNumberOfProductsStatistic(str);
        drinks.put(str, drinks.get(str) + 10);
        System.out.println("Зареждане на напитки: " + str);
      }
    }
  }
```



The program continues by collecting the bills from the clients and calculates the for the waiters and the barman

We are printing the statistics and the most ordered product.
```java
restaurant.getStorage().printNumberOfOrdersStatistic();
```

If a product is not ordered enough and doesn't have any popularity among the clients,
we are removing the product from the menu

At the end we are calculating the bonus per every employee and we are adding it to their salary
```java
restaurant.getWaiter1().addsBonusToSalary();
    restaurant.getWaiter2().addsBonusToSalary();
    restaurant.getBartender().addsBonusToSalary();
    restaurant.getHostess().addsBonusToSalary();
    restaurant.getCheff().addsBonusToSalary();
```


## UML FILE SOURCE
The uml file is located in /team-3/src/uml/team-3.uml

## ChangeLog
```
An update of the program functionalities was made by Team 7 on 11th of September 2022
- The restaurant now gives information 
  for one month on a random basis - new Enum CalendarType, that supports 
  random choosen month and year between 2022 and 2027 - done by Ana Popova;
- The storage quantities are reloaded daily, two files for food and rinks are created,
  and we take information from them the remaining quantities in the end 
  of each working day - done by Ivan Mitev;
- There is management unit that decreases the daily turnover
  for storage loading - done by Shtiliyan Karamfilov, Petar Dimitrov;
- The management unit pais salaries and bonusses of the personnel,
  accounting of the restaurant, and suports financial statistics - done by Shtiliyan Karamfilov, Petar Dimitrov;
```
## Authors
```
Team #3
Ivan Hadzhiev, Yulian Todorov, Ivaylo Vatev and Velislav Angelov
```
## Contributors
```
Team #7
Ivan Mitev, Shtiliyan Karamfilov, Petar Dimitrov and Ana Popova
```
