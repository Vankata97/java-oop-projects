package staff;

import clients.Client;
import restaurant.Bar;
import restaurant.Table;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

public class Hostess extends Staff
{

  public Hostess(String name, BigDecimal salary)
  {
    super(name, salary);
  }

  public void separateClients(List<Client> clients, List<Table> tables, Bar bar)
  {
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

  public void separateClientsIntoTables(List<Client> clients, List<Table> tables)
  {
    Random random = new Random();
    int clientsPerTable;

    for (Table table : tables) {
//      clientsPerTable = random.nextInt(2, 5);
      clientsPerTable = random.nextInt(3) + 2; //за JAVA 11

      if (clients.size() < clientsPerTable) {
        clientsPerTable = clients.size();
      }

      System.out.println("Брой клиенти на маса " + table.getTableNumber() + ": " + clientsPerTable);

      if (clients.size() != 0) {
        if (!table.isTableBusy()) {
          for (int j = clientsPerTable - 1; j >= 0; j--) {
            if (!clients
                .get(j)
                .isOnTheTable()) {
              table.addClientToTable(clients.get(j));
              clients
                  .get(j)
                  .setOnTheTable(true);
              clients.remove(clients.get(j));
            }
          }

          table.setTableBusy(true);
          table.setOrderTaken(false);
        }
      }
    }
  }


  @Override
  public String toString()
  {
    return "staff.Hostess{}";
  }
}
