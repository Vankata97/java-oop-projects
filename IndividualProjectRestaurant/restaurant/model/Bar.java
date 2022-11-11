package restaurant.model;

public class Bar {

  private Integer seats;
  private Integer usedSeats;
  private Employee currentBartender;

  public Bar(Integer seats) {
    this.seats = seats;
    this.usedSeats = 0;
  }

  public Employee getCurrentBartender() {
    return currentBartender;
  }

  public void setCurrentBartender(Employee currentBartender) {
    this.currentBartender = currentBartender;
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
}
