/**
 * Methods engaged with the working processes per day and per month.
 * Team 7
 */

import java.io.IOException;
import java.math.BigDecimal;

import restaurant.*;
import storage.Storage;

import static restaurant.Menu.*;

public final class Starter
{
  public static void showRestaurant(Restaurant restaurant, Management manager, Storage inventory, Menu menu) throws IOException
  {

    System.out.println();
    restaurant.separateClientsToStaff(restaurant.getClients(), restaurant.getTables(),
        restaurant.getBar());
    restaurant.getBartender().removeDrinksFromStorage(restaurant.getStorage());
    restaurant.getCheff().removeFoodsFromStorage(restaurant.getStorage());
    BigDecimal waiter1MoneyFromClients = restaurant.getWaiter1().getMoneyFromClients();
    BigDecimal waiter2MoneyFromClients = restaurant.getWaiter2().getMoneyFromClients();
    BigDecimal bartenderMoneyFromClients = restaurant.getBartender().getMoneyFromClients();
    restaurant.getWaiter1().addBillToRestaurant(waiter1MoneyFromClients);
    restaurant.getWaiter2().addBillToRestaurant(waiter2MoneyFromClients);
    restaurant.getBartender().addBillToRestaurant(bartenderMoneyFromClients);


    restaurant.getStorage().printStorage();
    manager.loadDailyStock(restaurant, menu);
    restaurant.getStorage().printNumberOfOrdersStatistic();
    System.out.println();

    restaurant.getMenu().getMostOrdered(restaurant.getStorage());
    restaurant.getMenu().removeLeastOrdered(restaurant.getStorage());
    System.out.println();
    restaurant.printStaffTip();
    System.out.println(
        "Добавя към ресторанта от сервитьор " + restaurant.getWaiter1().getName() + " каса: "
            + CYAN_BOLD + waiter1MoneyFromClients + RESET + " лв.");
    System.out.println(
        "Добавя към ресторанта от сервитьор " + restaurant.getWaiter2().getName() + " каса: " +
            CYAN_BOLD + waiter2MoneyFromClients + RESET + " лв.");
    System.out.println(
        "Добавя към ресторанта от барман " + restaurant.getBartender().getName() + " каса: "
            + CYAN_BOLD + bartenderMoneyFromClients + RESET + " лв.");
  }

  //добавен метод за месечната статистика оборот/заплати на персонала
  public static void monthlyStatistic(int year, Restaurant restaurant, CalendarType months)
  {
    System.out.println(RED_BOLD + "*******************************" + RESET);
    System.out.println("Оборота за месец " + months.getName() + " " + year + " е: " + CYAN_ITALIC + Management.getInitialAmount() + RESET + " лв.");
    System.out.println("Заплати за месец " + months.getName() + " " + year + " е: " + CYAN_ITALIC + restaurant.getAllSalaries() + RESET + " лв.");
    System.out.println("Чиста печалба за месец " + months.getName() + " " + year + " е: " + CYAN_ITALIC + Management.getInitialAmount().subtract(
        restaurant.getAllSalaries()) + RESET + " лв.");
    System.out.println(RED_BOLD + "*******************************" + RESET);
  }
}
