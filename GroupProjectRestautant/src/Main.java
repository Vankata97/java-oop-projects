/**
 * Basic methods are moved to the Starter class.
 * Here we just start simulation of one month working restaurant.
 * Team 7
 */

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Random;
import restaurant.*;
import storage.Storage;

import static restaurant.Menu.*;

public class Main
{
  public static void main(String[] args) throws IOException
  {
    //избираме случаен месец в периода Януари 2022 Декември 2027
    CalendarType[] months = CalendarType.values();
    int month = new Random().nextInt(12);
    int year = new Random().nextInt(5) + 2022;
    months[month].setYear(year);

    Menu menu = new Menu();
    System.out.println(menu);
    Storage storage = new Storage();
    storage.createStorageFile();

    //правим цикъл, който се върти броя дни в случайно избрания месец
    for (int i = 0; i < months[month].getNumberOfDays(); i++) {
      System.out.println(RED_BOLD + "Ден №" + (i + 1) + RESET);
      Restaurant restaurant = new Restaurant();
      BigDecimal dailyTurn;
      dailyTurn = Management.getInitialAmount();
      Management manager = new Management();
      Starter.showRestaurant(restaurant, manager, storage, menu);
      System.out.println("Чиста печалба за деня - " + (CYAN_BOLD + Management.getInitialAmount().subtract(dailyTurn)) +" лв."+ '\n' + RESET);

      if (i == months[month].getNumberOfDays() - 1) {
        manager.addsBonusToSalary(restaurant.getWaiter1());
        manager.addsBonusToSalary(restaurant.getWaiter2());
        manager.addsBonusToSalary(restaurant.getBartender());
        manager.addsBonusToSalary(restaurant.getHostess());
        manager.addsBonusToSalary(restaurant.getCheff());

        Starter.monthlyStatistic(year, restaurant, months[month]);
      }
    }
  }
}
