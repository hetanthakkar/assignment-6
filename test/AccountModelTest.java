import org.junit.Test;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import model.Account;
import model.AccountModel;
import model.ApiProcessor;
import model.FetchApi;

import static org.junit.Assert.assertEquals;

/**
 * AccountModelTest is a test class that checks functions of AccountModel objects are working.
 */
public class AccountModelTest {

  AccountModel newAccount = new Account();

  @Test
  public void addShareInflexible() throws Exception {
    newAccount.setPortfolioName("AlexInflexible", "inflexible");
    newAccount.addShare("AAPL", 5);
    newAccount.finishBuild();
    String completeMessage = "Done!";
    assertEquals("Done!", completeMessage);
  }

  @Test
  public void getPortfolioCompositionInflexible() throws Exception {
    newAccount.setPortfolioName("HelloInflexible", "inflexible");
    newAccount.addShare("AAPL", 5);
    newAccount.addShare("GOOG", 5);
    newAccount.finishBuild();
    String expectedOutput = "HelloInflexible\n |--- ( GOOG, 5.0) \n |--- ( AAPL, 5.0) \n";
    assertEquals(expectedOutput, newAccount.getPortfolioComposition("HelloInflexible"));
  }

  @Test
  public void getPortfolioValueInflexible() throws Exception {
    String testPortfolioName = "Alex1Inflexible";
    String testStock1 = "AMZN";
    int quantity1 = 1;
    String testStock2 = "GOOG";
    int quantity2 = 1;
    String testDate = "2024-03-12";
    newAccount.setPortfolioName(testPortfolioName, "inflexible");
    newAccount.addShare(testStock1, quantity1);
    newAccount.addShare(testStock2, quantity2);
    newAccount.finishBuild();

    FetchApi fetchApi = new FetchApi();
    String apiResponse1 = fetchApi.fetchData(testStock1, testDate);
    String apiResponse2 = fetchApi.fetchData(testStock2, testDate);

    ApiProcessor apiProcessor1 = new ApiProcessor(apiResponse1);
    ApiProcessor apiProcessor2 = new ApiProcessor(apiResponse2);

    double expectedOutput =
            ((Double.parseDouble(apiProcessor1.getApiPrice()) * quantity1)
                    + (Double.parseDouble(apiProcessor2.getApiPrice()) * quantity2));
    String testOutput = newAccount.getPortfolioTotalValueAtCertainDate(testPortfolioName, testDate);
    assertEquals(testPortfolioName + " Value: $" + expectedOutput, testOutput);
  }


  @Test(expected = Exception.class)
  public void createEmptyPortfolioInflexible() throws Exception {
    String portName = "EmptyPortInflexible";
    newAccount.setPortfolioName(portName, "inflexible");
    newAccount.finishBuild();
  }

  @Test(expected = Exception.class)
  public void buyShareInflexible() throws Exception {
    String portName = "BuyShareInflexible";
    String testShare = "MSFT";
    String date = "2024-01-15";
    int testQuantity = 1;
    newAccount.setPortfolioName(portName, "inflexible");
    newAccount.addShare(testShare, testQuantity);
    newAccount.finishBuild();
    newAccount.buyShare(portName, testShare, testQuantity, date);
  }

  @Test(expected = Exception.class)
  public void sellShareInflexible() throws Exception {
    String portName = "SellShareInflexible";
    String testShare = "MSFT";
    String date = "2024-01-15";
    int testQuantity = 1;
    newAccount.setPortfolioName(portName, "inflexible");
    newAccount.addShare(testShare, testQuantity);
    newAccount.finishBuild();
    newAccount.sellShare(portName, testShare, testQuantity, date);
  }

  // ----------

  @Test
  public void addShareFlexible() throws Exception {
    newAccount.setPortfolioName("Alexflexible", "flexible");
    newAccount.addShare("AAPL", 5);
    newAccount.finishBuild();
    String completeMessage = "Done!";
    assertEquals("Done!", completeMessage);
  }

  @Test
  public void getPortfolioCompositionFlexible() throws Exception {
    newAccount.setPortfolioName("Helloflexible", "flexible");
    newAccount.addShare("AAPL", 5);
    newAccount.addShare("GOOG", 5);
    newAccount.finishBuild();
    String expectedOutput = "Helloflexible\n |--- ( GOOG, 5.0) \n |--- ( AAPL, 5.0) \n";
    assertEquals(expectedOutput, newAccount.getPortfolioComposition("Helloflexible"));
  }

  @Test
  public void getPortfolioValueFlexibleOnCreationDate() throws Exception {
    String testPortfolioName = "Alex1Flexible";
    String testStock1 = "AMZN";
    int quantity1 = 1;
    String testStock2 = "GOOG";
    int quantity2 = 1;
    String testDate = LocalDate.now().toString();
    newAccount.setPortfolioName(testPortfolioName, "flexible");
    newAccount.addShare(testStock1, quantity1);
    newAccount.addShare(testStock2, quantity2);
    newAccount.finishBuild();

    FetchApi fetchApi = new FetchApi();
    String apiResponse1 = fetchApi.fetchData(testStock1, testDate);
    String apiResponse2 = fetchApi.fetchData(testStock2, testDate);

    ApiProcessor apiProcessor1 = new ApiProcessor(apiResponse1);
    ApiProcessor apiProcessor2 = new ApiProcessor(apiResponse2);

    double expectedOutput =
            ((Double.parseDouble(apiProcessor1.getApiPrice()) * quantity1)
                    + (Double.parseDouble(apiProcessor2.getApiPrice()) * quantity2));
    String testOutput = newAccount.getPortfolioTotalValueAtCertainDate(testPortfolioName, testDate);
    assertEquals(testPortfolioName + " Value: $" + expectedOutput, testOutput);
  }

  @Test
  public void getPortfolioValueFlexibleBeforeCreationDate() throws Exception {
    String testPortfolioName = "Alex2Flexible";
    String testStock1 = "AMZN";
    int quantity1 = 1;
    String testStock2 = "GOOG";
    int quantity2 = 1;
    String testDate = "2024-03-12";
    newAccount.setPortfolioName(testPortfolioName, "flexible");
    newAccount.addShare(testStock1, quantity1);
    newAccount.addShare(testStock2, quantity2);
    newAccount.finishBuild();
    String testOutput = newAccount.getPortfolioTotalValueAtCertainDate(testPortfolioName, testDate);
    assertEquals(testPortfolioName + " Value: $" + 0.0, testOutput);
  }

  @Test(expected = Exception.class)
  public void getPortfolioValueFutureDateGetErrorMessageFlexible() throws Exception {
    String testPortfolioName = "Alex2Flexible";
    String testStock1 = "AMZN";
    int quantity1 = 1;
    String testStock2 = "TSLA";
    int quantity2 = 1;
    newAccount.setPortfolioName(testPortfolioName, "flexible");
    newAccount.addShare(testStock1, quantity1);
    newAccount.addShare(testStock2, quantity2);
    newAccount.finishBuild();
    String testOutput =
            newAccount.getPortfolioTotalValueAtCertainDate(testPortfolioName, "2025-03-03");
  }

  @Test(expected = Exception.class)
  public void createEmptyPortfolioFlexible() throws Exception {
    String portName = "EmptyPortFlexible";
    newAccount.setPortfolioName(portName, "flexible");
    newAccount.finishBuild();
  }

  @Test
  public void buyShareFlexible() throws Exception {
    String portName = "BuyShareFlexible";
    String testShare = "MSFT";
    String date = "2024-01-15";
    int testQuantity = 1;
    newAccount.setPortfolioName(portName, "flexible");
    newAccount.addShare(testShare, testQuantity);
    newAccount.finishBuild();
    newAccount.buyShare(portName, testShare, testQuantity, date);
    String expectedOutput = "BuyShareFlexible\n |--- ( MSFT, 2.0) \n";
    assertEquals(expectedOutput, newAccount.getPortfolioComposition("BuyShareFlexible"));
  }

  @Test
  public void sellShareFlexible() throws Exception {
    String portName = "SellShareFlexible";
    String testShare = "MSFT";
    String date = LocalDate.now().toString();
    int testQuantity = 1;
    newAccount.setPortfolioName(portName, "flexible");
    newAccount.addShare(testShare, testQuantity);
    newAccount.finishBuild();
    newAccount.sellShare(portName, testShare, testQuantity, date);
    String expectedOutput = "SellShareFlexible\n";
    assertEquals(expectedOutput, newAccount.getPortfolioComposition("SellShareFlexible"));
  }

  @Test(expected = Exception.class)
  public void sellShareFlexibleErrorNotEnough() throws Exception {
    String portName = "SellShareFlexible1";
    String testShare = "MSFT";
    String date = LocalDate.now().toString();
    int testQuantity = 1;
    newAccount.setPortfolioName(portName, "flexible");
    newAccount.addShare(testShare, testQuantity);
    newAccount.finishBuild();
    newAccount.sellShare(portName, testShare, testQuantity + 1, date);
  }

  @Test(expected = Exception.class)
  public void sellShareFlexibleErrorNoTicker() throws Exception {
    String portName = "SellShareFlexible2";
    String testShare = "MSFT";
    String testShare2 = "AAPL";
    String date = LocalDate.now().toString();
    int testQuantity = 1;
    newAccount.setPortfolioName(portName, "flexible");
    newAccount.addShare(testShare, testQuantity);
    newAccount.finishBuild();
    newAccount.sellShare(portName, testShare2, testQuantity, date);
  }

  @Test
  public void buyStrategyFlexible() throws Exception {
    String portName = "BuyStrategyFlexible";
    String testShare = "MSFT";
    String testShare2 = "AAPL";
    String date = LocalDate.now().toString();
    int testQuantity = 1;
    newAccount.setPortfolioName(portName, "flexible");
    newAccount.addShare(testShare, testQuantity);
    newAccount.addShare(testShare2, testQuantity);
    newAccount.finishBuild();
    System.out.println(newAccount.getPortfolioComposition(portName));
    Map<String, Double> shares = new HashMap<String, Double>();
    shares.put("MSFT", 75.0);
    shares.put("AAPL", 25.0);
    newAccount.buyStrategy(portName, 10000, shares);
    System.out.println(newAccount.getPortfolioComposition(portName));
  }
}
