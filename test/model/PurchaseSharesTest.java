package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * PurchaseSharesTest is a test class to test PurchaseShares class functionality.
 */
public class PurchaseSharesTest {

  ShareModel share;

  @Test
  public void getCost() throws Exception {
    String testShare1 = "AAPL";
    int quantity1 = 2;

    share = new PurchaseShares(testShare1, quantity1);
    double actual = share.getCost();

    FetchApiInterface fetchApi = new FetchApi();
    String apiResponse1 = fetchApi.fetchData(testShare1);

    ApiProcessorInterface apiProcessor1 = new ApiProcessor(apiResponse1);

    double expectedOutput = (Double.parseDouble(apiProcessor1.getApiPrice()) * quantity1);
    assertEquals(expectedOutput, actual, 0.009);
  }

  @Test
  public void getCurrentValue() throws Exception {
    String testShare1 = "AAPL";
    int quantity1 = 2;

    share = new PurchaseShares(testShare1, quantity1);
    double actual = share.getCost();

    FetchApiInterface fetchApi = new FetchApi();
    String apiResponse1 = fetchApi.fetchData(testShare1);

    ApiProcessorInterface apiProcessor1 = new ApiProcessor(apiResponse1);

    double expectedOutput = (Double.parseDouble(apiProcessor1.getApiPrice()) * quantity1);
    assertEquals(expectedOutput, actual, 0.009);
  }

  @Test
  public void getQuantity() throws Exception {
    String testShare1 = "AAPL";
    int quantity1 = 2;

    share = new PurchaseShares(testShare1, quantity1);
    assertEquals(quantity1, share.getQuantity());
  }

  @Test
  public void getValueAtDate() throws Exception {
    String testShare1 = "AAPL";
    int quantity1 = 2;
    String testDate = "2024-03-12";

    share = new PurchaseShares(testShare1, quantity1);

    FetchApiInterface fetchApi = new FetchApi();
    String apiResponse1 = fetchApi.fetchData(testShare1, testDate);

    ApiProcessorInterface apiProcessor1 = new ApiProcessor(apiResponse1);

    double expectedOutput = (Double.parseDouble(apiProcessor1.getApiPrice()) * quantity1);
    assertEquals(expectedOutput, share.getValueAtDate(testDate), 0.009);
  }
}