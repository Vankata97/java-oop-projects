package interfaces;

import java.math.BigDecimal;
import restaurant.Restaurant;

public interface Billable {

  void addBillToRestaurant(BigDecimal money);
}
