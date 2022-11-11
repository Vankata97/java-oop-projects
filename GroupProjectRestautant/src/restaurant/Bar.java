package restaurant;

import clients.Client;
import java.util.ArrayList;
import java.util.List;

public class Bar {

  private static final int BAR_CAPACITY = 10;
  private int barCapacity;
  private boolean isBusy;

  private List<Client> barClients;

  public Bar() {
    this.barCapacity = BAR_CAPACITY;
    this.isBusy = false;
    this.barClients = new ArrayList<>();
  }

  public int getBarCapacity() {
    return barCapacity;
  }

  public void setBusy(boolean busy) {
    isBusy = busy;
  }

  public List<Client> getBarClients() {
    return barClients;
  }

  public void setBarClients(List<Client> barClients) {
    this.barClients = barClients;
  }

  public void addClient(Client client) {
    this.barClients.add(client);
  }

  @Override
  public String toString() {
    return "restaurant.Bar{" +
        "capacity=" + barCapacity +
        ", isBusy=" + isBusy +
        ", barClients=" + barClients +
        '}';
  }
}
