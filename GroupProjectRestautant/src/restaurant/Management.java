package restaurant;
/**
 * Management class decreases the daily turnover for storage loading.
 * Here salaries and bonuses of the personnel are paid,
 * accounting of the restaurant s done, and supports financial statistics.
 * done by Shtiliyan Karamfilov and Petar Dimitrov
 */

import interfaces.Billable;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Random;
import staff.Staff;
import storage.Item;
import storage.Storage;

import static restaurant.Menu.*;

public final class Management implements Billable
{

  private static BigDecimal initialAmount = new BigDecimal("1000");  //начална сума за всеки работен ден

  public static BigDecimal getInitialAmount()
  {
    return initialAmount;
  }

  public static void setInitialAmount(BigDecimal money)
  {
    initialAmount = money;
  }

  public static void removeInitialAmount(BigDecimal money)
  {
    initialAmount = initialAmount.subtract(money);
  }

  @Override
  public void addBillToRestaurant(BigDecimal moneyFromClients)
  {
    initialAmount = initialAmount.add(moneyFromClients);
  }

  //Добавя бонус към заплатата със вероятност 40%
  public void addsBonusToSalary(Staff employee)
  {
    Random rn = new Random();
    int bonus = rn.nextInt(10) + 1;
    if (bonus > 6) {
      System.out.println("Добавени 200 лв. към заплатата на " + YELLOW_BOLD+employee.getName()+RESET + " !");
      employee.setSalary(employee
          .getSalary()
          .add(new BigDecimal("200")));
      System.out.println("Твоята заплата е: " +CYAN_BOLD+ employee.getSalary() + RESET+" лв.");
    }
  }

  //зарежда складовите наличности с напитки и вади пари от оборота за зареденото количество
  public BigDecimal loadDrink(String drinkName, Storage inventory, Integer amount, Menu menu)
  {
    BigDecimal price = new BigDecimal("0");
    for (Item alcoholicDrink : menu.getAlcoholicDrinks()) {
      if (drinkName.equals(alcoholicDrink.getName())) {
        price = alcoholicDrink.getPrice();
        break;
      }
    }
    if (price.equals(new BigDecimal("0"))) {
      for (Item softDrinks : menu.getSoftDrinks()) {
        if (drinkName.equals(softDrinks.getName())) {
          price = softDrinks.getPrice();
          break;
        }
      }
    }

    BigDecimal totalPrice = (price
        .multiply(new BigDecimal("0.5"))
        .multiply(new BigDecimal(amount)));

    Management.removeInitialAmount(totalPrice);
    return totalPrice;
  }

  //зарежда складовите наличности с храна и вади пари от оборота за зареденото количество
  public BigDecimal loadFood(String foodName, Storage inventory, Integer amount, Menu menu)
  {
    BigDecimal price = new BigDecimal("0");

    for (Item salads : menu.getSalads()) {
      if (foodName.equals(salads.getName())) {
        price = salads.getPrice();
        break;
      }
    }
    if (price.equals(new BigDecimal("0"))) {
      for (Item mainDishes : menu.getMainDishes()) {
        if (foodName.equals(mainDishes.getName())) {
          price = mainDishes.getPrice();
          break;
        }
      }
    }

    if (price.equals(new BigDecimal("0"))) {

      for (Item desserts : menu.getDesserts()) {
        if (foodName.equals(desserts.getName())) {
          price = desserts.getPrice();
          break;
        }
      }
    }

    BigDecimal totalPrice = (price
        .multiply(new BigDecimal("0.5"))
        .multiply(new BigDecimal(amount)));

    Management.removeInitialAmount(totalPrice);
    return totalPrice;

  }

  //проверява складовите наличности и зарежда максималното количество
  public BigDecimal loadDailyStock(Restaurant restaurant, Menu menu)
  {
    Storage inventory = restaurant.getStorage();
    BigDecimal loadSum = new BigDecimal("0");
    for (Map.Entry<String, Integer> stock : inventory
        .getDrinks()
        .entrySet()) {
      int amount = 50 - stock.getValue();
      String name = stock.getKey();
      loadSum = loadSum.add(loadDrink(name, inventory, amount, menu));
    }
    for (Map.Entry<String, Integer> stock : inventory
        .getFoods()
        .entrySet()) {
      int amount = 50 - stock.getValue();
      String name = stock.getKey();
      loadSum = loadSum.add(loadFood(name, inventory, amount, menu));
    }
    return loadSum;
  }
}
