package restaurant.model;


import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import restaurant.util.CommonUtil;


public class Menu {

  private List<Product> products;

  public Menu() {
    this.products = new ArrayList<>();

    try {
      fillProducts();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public BigDecimal getProductPrice(String productName) {
    Product foundedProduct = getAllProducts().stream()
        .filter(e -> e.getName().equals(productName))
        .collect(Collectors.toList())
        .get(0);

    return foundedProduct.getPrice().setScale(2, RoundingMode.HALF_UP);
  }

  private Product getProductByName(String productName) {
    List<Product> allProduct = getAllProducts();

    return allProduct.stream()
        .filter(product -> product.getName().equals(productName))
        .collect(Collectors.toList())
        .get(0);
  }

  public List<Product> getAllProducts() {
    return products;
  }

  private void fillProducts() throws IOException {
    List<String> productsData = Files.readAllLines(Paths.get(
        CommonUtil.DATA_PATH + CommonUtil.PRODUCTS_FILE_NAME));
    for (String singleProductData : productsData) {
      String[] data = singleProductData.split("\\|");

      // Това ще преобразува името на категорията, така че да може да го намерим енумерацията
      Category category = Category.valueOf(CommonUtil.formatString(data[0]));
      String productName = data[1];
      BigDecimal productPrice = new BigDecimal(data[2]);
      Integer weight = Integer.parseInt(data[3]);

      Product product = new Product(productName, productPrice, category, weight);
      this.products.add(product);
    }
  }

  public void stopSell(String worstProduct) {
    Product product = getProductByName(worstProduct);
    this.products.remove(product);
  }

  public void changeProductPriceByProductName(String bestSeller) {
    Product product = getProductByName(bestSeller);
    product.setPrice(product.getPrice().multiply(BigDecimal.valueOf(1.10)));
  }
}
