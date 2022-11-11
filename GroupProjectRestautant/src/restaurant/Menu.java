package restaurant;

import java.util.Arrays;

import storage.Item;
import storage.Storage;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Menu
{
  //за улеснение модификатора за достъп на цветовете беше променен от PRIVATE на PUBLIC
  public static final String GREEN_ITALIC  = "\033[3;32m";
  public static final String YELLOW_ITALIC = "\033[3;33m";
  public static final String BLUE_ITALIC   = "\033[3;34m";
  public static final String CYAN_ITALIC   = "\033[3;36m";
  public static final String WHITE_ITALIC  = "\033[3;37m";
  public static final String RED_BOLD      = "\033[1;31m";
  public static final String GREEN_BOLD    = "\033[1;32m";
  public static final String YELLOW_BOLD   = "\033[1;33m";
  public static final String BLUE_BOLD     = "\033[1;34m";
  public static final String CYAN_BOLD     = "\033[1;36m";
  public static final String WHITE_BOLD    = "\033[1;37m";
  public static final String RESET         = "\u001B[0m";

  private List<Item> salads;
  private List<Item> mainDishes;
  private List<Item> desserts;
  private List<Item> alcoholicDrinks;
  private List<Item> softDrinks;

  public Menu()
  {
    this.salads = new ArrayList<>();
    this.mainDishes = new ArrayList<>();
    this.desserts = new ArrayList<>();
    this.alcoholicDrinks = new ArrayList<>();
    this.softDrinks = new ArrayList<>();
    fillMenu();
  }

  private void removeMainDishOrSalad(String name)
  {
    for (int i = salads.size() - 1; i >= 0; i--) {

      if (salads.get(i).getName().equals(name)) {
        System.out.println("От менюто беше премахнато: " + RED_BOLD+salads.get(i).getName()+RESET);
        salads.remove(salads.get(i));
      }
    }
    for (int i = mainDishes.size() - 1; i >= 0; i--) {

      if (mainDishes.get(i).getName().equals(name)) {
        System.out.println( "От менюто беше премахнато: " +RED_BOLD + mainDishes.get(i).getName() + RESET);
        mainDishes.remove(mainDishes.get(i));

      }
    }
  }

  public void getMostOrdered(Storage storage)
  {
    int max = Integer.MIN_VALUE;
    String name = "";
    for (Map.Entry<String, Integer> item : storage.getNumberOfOrdersStatistic().entrySet()) {
      if (isInTheList(mainDishes, item) || isInTheList(salads, item)) {
        if (max < item.getValue()) {
          max = item.getValue();
          name = item.getKey();
        }
      }
    }
    System.out.println( "Най-поръчваното ястие за деня е: "+YELLOW_BOLD + name + RESET);
  }

  public void removeLeastOrdered(Storage storage)
  {
    int min = Integer.MAX_VALUE;
    String name = "";
    for (Map.Entry<String, Integer> item : storage.getNumberOfOrdersStatistic().entrySet()) {
      if (isInTheList(mainDishes, item) || isInTheList(salads, item)) {
        if (min > item.getValue()) {
          min = item.getValue();
          name = item.getKey();
        }
      }
    }

    System.out.println("Най-малко поръчваното ястие за деня е: " + RED_BOLD +name+RESET);
    removeMainDishOrSalad(name);
  }

  public void setAlcoholicDrinks(List<Item> alcoholicDrinks)
  {
    this.alcoholicDrinks = alcoholicDrinks;
  }

  public List<Item> getSalads()
  {
    return salads;
  }

  public List<Item> getMainDishes()
  {
    return mainDishes;
  }

  public List<Item> getDesserts()
  {
    return desserts;
  }

  public List<Item> getAlcoholicDrinks()
  {
    return alcoholicDrinks;
  }

  public List<Item> getSoftDrinks()
  {
    return softDrinks;
  }

  private String removeBracelet(String string)
  {
    string = string.replaceAll("\\[", "");
    string = string.replaceAll("]", "");
    return string;
  }

  private boolean isInTheList(List<Item> items, Map.Entry<String, Integer> map)
  {
    boolean flag = false;
    for (Item i : items) {
      if (i.getName().equals(map.getKey())) {
        flag = true;
      }
    }
    return flag;
  }

  private void fillMenu()
  {
    salads.add(new Item("Шопска салата", "food", 0.450, new BigDecimal("5.80")));
    salads.add(new Item("Овчарска салата", "food", 0.400, new BigDecimal("6.50")));
    salads.add(new Item("Зелена салата", "food", 0.300, new BigDecimal("5.20")));
    salads.add(new Item("Гръцка салата", "food", 0.400, new BigDecimal("5.80")));
    salads.add(new Item("Италианска салата", "food", 0.400, new BigDecimal("6.50")));

    mainDishes.add(new Item("Пица", "food", 1, new BigDecimal("7.50")));
    mainDishes.add(new Item("Спагети", "food", 0.400, new BigDecimal("7.50")));
    mainDishes.add(new Item("Омлет", "food", 1, new BigDecimal("6.50")));
    mainDishes.add(new Item("Калмари", "food", 0.200, new BigDecimal("8.70")));
    mainDishes.add(new Item("Кюфтета", "food", 3, new BigDecimal("5.70")));
    mainDishes.add(new Item("Пържени картофи", "food", 0.300, new BigDecimal("6.50")));
    mainDishes.add(new Item("Пържола", "food", 1, new BigDecimal("7.60")));
    mainDishes.add(new Item("Кебапчета", "food", 3, new BigDecimal("5.70")));
    mainDishes.add(new Item("Виенски шницел", "food", 1, new BigDecimal("10.50")));
    mainDishes.add(new Item("Яйца на очи", "food", 3, new BigDecimal("4.50")));

    desserts.add(new Item("Сладолед", "food", 0.200, new BigDecimal("4.50")));
    desserts.add(new Item("Торта", "food", 1, new BigDecimal("4.20")));

    alcoholicDrinks.add(new Item("Ракия", "drink", 0.050, new BigDecimal("3.50")));
    alcoholicDrinks.add(new Item("Вино", "drink", 0.200, new BigDecimal("4.30")));
    alcoholicDrinks.add(new Item("Бира", "drink", 1, new BigDecimal("2.80")));

    softDrinks.add(new Item("Сок", "drink", 0.200, new BigDecimal("2.20")));
    softDrinks.add(new Item("Кола", "drink", 0.200, new BigDecimal("2.50")));
    softDrinks.add(new Item("Минерална вода", "drink", 0.200, new BigDecimal("2.50")));
    softDrinks.add(new Item("Айрян", "drink", 0.300, new BigDecimal("1.50")));
  }

  public String printRestaurantName()
  {
    String line =
        "||======||  ||======||  ||    /||      ||======||   ||=======      /\\\\        /\\\\       /\\\\             ======||\n"
            +
            "||      ||  ||      ||  ||   //||          ||       ||            //\\\\       //\\\\      // \\\\                  ||\n"
            +
            "||      ||  ||======||  ||  // ||          ||       ||======     //  \\\\     //  \\\\    //   \\\\    ===     =====||\n"
            +
            "||      ||  ||          || //  ||          ||       ||          //====\\\\   //    \\\\  //     \\\\                ||\n"
            +
            "||      ||  ||          ||//   ||          ||       ||=======  //      \\\\ //      \\\\//       \\\\         ======||\n";
    String printName = RESET
        + "----------------------------------------------------------------------------------------------------------------\n"
        + line
        + "----------------------------------------------------------------------------------------------------------------\n\n";
    return printName;
  }


  @Override
  public String toString()
  {
    return printRestaurantName() + RED_BOLD + "--------" + "| МЕНЮ |" + "--------\n" +
        GREEN_BOLD + "\n---САЛАТИ---\n" + GREEN_ITALIC + removeBracelet(salads.toString()) + "\n" +
        YELLOW_BOLD + "\n---ОСНОВНИ ЯСТИЯ---\n" + YELLOW_ITALIC + removeBracelet(
        mainDishes.toString()) + "\n" +
        WHITE_BOLD + "\n---ДЕСЕРТИ---\n" + WHITE_ITALIC + removeBracelet(desserts.toString()) + "\n"
        +
        CYAN_BOLD + "\n---БЕЗАЛКОХОЛНИ НАПИТКИ---\n" + CYAN_ITALIC + removeBracelet(
        softDrinks.toString()) + "\n" +
        BLUE_BOLD + "\n---АЛКОХОЛНИ НАПИТКИ---\n" + BLUE_ITALIC + removeBracelet(
        alcoholicDrinks.toString()) + "\n" + RESET;
  }
}
