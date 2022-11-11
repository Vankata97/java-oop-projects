package restaurant.model;

public class Table {

  private Integer number;
  private Integer seats;
  private Integer usedSeats;
  private Employee currentWaiter;

  public Table(Integer number, Integer seats) {
    this.number = number;
    this.seats = seats;
    this.usedSeats = 0;
  }

  public Integer getSeats() {
    return seats;
  }

  public void setSeats(Integer seats) {
    this.seats = seats;
  }

  public Integer getUsedSeats() {
    return usedSeats;
  }

  public void setUsedSeats(Integer usedSeats) {
    this.usedSeats = usedSeats;
  }

  public Integer getNumber() {
    return number;
  }

  public void setNumber(Integer number) {
    this.number = number;
  }

  public boolean isTableFull() {
    return this.usedSeats > 0;
  }

  public Employee getCurrentWaiter() {
    return (Employee) currentWaiter;
  }

  public void setCurrentWaiter(Employee currentWaiter) {
    this.currentWaiter = currentWaiter;
  }
}
