package restaurant.model;

import java.util.Objects;

public class Client {

  private String name;

  public Client(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }


  @Override
  public boolean equals(Object object) {
    if (object instanceof Client) {
      Client client = (Client) object;
      if (client.getName() != null) {
        return client.getName().equals(this.getName());
      }
    }

    return false;
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
