package storage;
/**
 The storage quantities are reloaded daily, two files for food and rinks are created,
 and we take information from them. Then we check the map for the remaining quantities in the end
 of each working day and reload to the maximum.- done by Ivan Mitev
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Storage
{

  //правим складови наличности от храна
  private Map<String, Integer> foods = new LinkedHashMap<>(Map.ofEntries(
      Map.entry("Шопска салата", 50),
      Map.entry("Овчарска салата", 50),
      Map.entry("Зелена салата", 50),
      Map.entry("Гръцка салата", 50),
      Map.entry("Италианска салата", 50),
      Map.entry("Пица", 50),
      Map.entry("Спагети", 50),
      Map.entry("Омлет", 50),
      Map.entry("Калмари", 50),
      Map.entry("Кюфтета", 50),
      Map.entry("Пържени картофи", 50),
      Map.entry("Пържола", 50),
      Map.entry("Кебапчета", 50),
      Map.entry("Виенски шницел", 50),
      Map.entry("Яйца на очи", 50),
      Map.entry("Сладолед", 50),
      Map.entry("Торта", 50)));

  //правим складови наличности от напитки
  private Map<String, Integer> drinks = new LinkedHashMap<>(Map.ofEntries(
      Map.entry("Ракия", 50),
      Map.entry("Вино", 50),
      Map.entry("Бира", 50),
      Map.entry("Сок", 50),
      Map.entry("Кола", 50),
      Map.entry("Минерална вода", 50),
      Map.entry("Айрян", 50)));
  private Map<String, Integer> numberOfOrdersStatistic; //статистика за продадените количества на продуктите

  public Storage()
  {
    this.numberOfOrdersStatistic = new LinkedHashMap<>();
    makeZeroValuesNumberOfOrderStatistic();
  }

  public Map<String, Integer> getFoods()
  {
    return foods;
  }

  public Map<String, Integer> getDrinks()
  {
    return drinks;
  }

  //чистим списъка с поръчки в края на деня
  private void makeZeroValuesNumberOfOrderStatistic()
  {
    numberOfOrdersStatistic.putAll(foods);
    numberOfOrdersStatistic.putAll(drinks);

    for (Map.Entry<String, Integer> m : numberOfOrdersStatistic.entrySet()) {
      m.setValue(0);
    }
  }

  //четем файла с наличности, за да заредим количествата за следващия ден
  public void decreaseFoodValue(String food) throws FileNotFoundException
  {
    BufferedReader reader = new BufferedReader(new FileReader("Foods.txt"));
    try {
      for (int i = 0; i < foods.size() - 1; i++) {

        String[] fileFoods = reader
            .readLine()
            .split(" - ");

        if (food.equals(fileFoods[0])) {

          if (foods.get(food) > 0) {
            foods.put(food, foods.get(food) - 1);
            increaseNumberOfProductsStatistic(food);
            break;
          }
          if (foods.get(food) == 0) {
            foods.put(food, foods.get(food) + 10);
            System.out.println("Зареждане на храни: " + food);
          }
        }
      }
    }
    catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void decreaseDrinkValue(String drink) throws FileNotFoundException
  {
    BufferedReader reader = new BufferedReader(new FileReader("Drinks.txt"));
    //четем файла с наличности, за да заредим количествата за следващия ден
    try {
      for (int i = 0; i < drinks.size() - 1; i++) {

        String[] fileDrinks = reader
            .readLine()
            .split(" - ");

        if (drink.equals(fileDrinks[0])) {

          if (drinks.get(drink) > 0) {
            drinks.put(drink, drinks.get(drink) - 1);
            increaseNumberOfProductsStatistic(drink);
            break;
          }
          if (drinks.get(drink) == 0) {
            drinks.put(drink, drinks.get(drink) + 10);
            System.out.println("Зареждане на напитки: " + drink);
          }
        }
      }
    }
    catch (IOException ioe) {
      throw new RuntimeException(ioe);
    }
  }


  public void increaseNumberOfProductsStatistic(String str)
  {
    numberOfOrdersStatistic.put(str, numberOfOrdersStatistic.get(str) + 1);
  }

  public Map<String, Integer> getNumberOfOrdersStatistic()
  {
    return numberOfOrdersStatistic;
  }

  //разпечатваме статистика на продадените храни и напитки в края на деня
  public void printStorage() throws IOException
  {
    System.out.println();
    System.out.println("-------------------------");
    System.out.println("Наличности в края на деня");
    System.out.println("-------------------------");
    System.out.println("=== Хранителни ===");
    foods.forEach((k, v) -> System.out.println("Продукт: " + k + " Брой: " + v));
    System.out.println();
    System.out.println("=== Напитки ===");
    drinks.forEach((k, v) -> System.out.println("Продукт: " + k + " Брой: " + v));

  }

  //принтираме статистика на поръчкте за един ден
  public void printNumberOfOrdersStatistic()
  {
    System.out.println();
    System.out.println("-----------------------");
    System.out.println("Статистика на поръчките");
    System.out.println("-----------------------");
    numberOfOrdersStatistic.forEach((k, v) -> System.out.println("Продукт: " + k + " Брой: " + v));
  }

  //създаваме файлове за складовите наличности
  public void createStorageFile() throws IOException
  {
    //обхождаме списъка с храни и записваме наличността
    BufferedWriter writer = new BufferedWriter(new FileWriter("Foods.txt"));
    for (String key : foods.keySet()) {
      writer.write(key + " - " + foods.get(key) + "\n");
    }
    //обхождаме списъка с напитки и записваме наличността
    BufferedWriter writer2 = new BufferedWriter(new FileWriter("Drinks.txt"));
    for (String key : drinks.keySet()) {
      writer2.write(key + " - " + drinks.get(key) + "\n");
    }
    /* Почистваме буфера и записваме всичко във файла,
    за да го затворим и освободим за ползване от друг ресурс.
     */
    writer.flush();
    writer.close();
    writer2.flush();
    writer2.close();
  }
}
