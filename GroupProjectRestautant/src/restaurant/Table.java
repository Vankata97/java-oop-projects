package restaurant;

import clients.Client;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;

public class Table {

  private static final int TABLE_CAPACITY = 4;
  private int tableNumber;
  private BigDecimal tableBill;
  private BigDecimal tableTip;
  private boolean isTableBusy;
  private boolean isOrderTaken;
  private int tableCapacity;

  private List<Client> clients;

  public Table(int tableNumber) {
    clients = new ArrayList<>();
    this.tableNumber = tableNumber;
    this.tableCapacity = TABLE_CAPACITY;
    this.isTableBusy = false;
    this.tableBill = new BigDecimal("0");
    this.tableTip = new BigDecimal("0");
    this.isOrderTaken = false;
  }

  public void addClientToTable(Client client) {
    if (!isTableBusy) {
      this.clients.add(client);
    } else {
      System.out.println("The table is busy");
    }
  }

  public BigDecimal getTableBill() {
    calculateTableBill();
    isTableBusy = false;
    return tableBill;
  }

  public BigDecimal getTableTip() {
    calculateTableTip();
    return tableTip;
  }

  public void setOrderTaken(boolean orderTaken) {
    isOrderTaken = orderTaken;
  }

  public boolean isOrderTaken() {
    return isOrderTaken;
  }

  public int getTableNumber() {
    return tableNumber;
  }

  public boolean isTableBusy() {
    return isTableBusy;
  }

  public List<Client> getClients() {
    return clients;
  }

  public void setTableBusy(boolean tableBusy) {
    isTableBusy = tableBusy;
  }

  public void setClients(List<Client> clients) {
    this.clients = clients;
  }

  private void calculateTableBill() {
    for (Client client : clients) {
      this.tableBill = this.tableBill.add(client.getClientBill()
          .setScale(2, RoundingMode.HALF_UP));
    }
  }

  private void calculateTableTip() {
    for (Client client : clients) {
      this.tableTip = this.tableTip.add(client.getClientTip()
          .setScale(2, RoundingMode.HALF_UP));
    }
  }

  @Override
  public String toString() {
    return "restaurant.Table{" +
        "tableNumber=" + tableNumber +
        ", bill=" + tableBill +
        ", isBusy=" + isTableBusy +
        ", isOrderTaken=" + isOrderTaken +
        ", capacity=" + tableCapacity +
        ", tip=" + tableTip +
        ", clients=" + clients +
        '}';
  }
}
