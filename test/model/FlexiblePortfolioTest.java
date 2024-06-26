package model;

import org.junit.Test;

import java.time.LocalDate;

import model.FlexiblePortfolio.FlexiblePortfolioBuilder;

import static org.junit.Assert.assertEquals;

/**
 * The FlexiblePortfolioTest class contains unit tests for the FlexiblePortfolio class.
 */

public class FlexiblePortfolioTest extends AbstractPortfolioTest {

  @Test(expected = Exception.class)
  public void invalidPortfolio() throws Exception {
    testPortfolio = new FlexiblePortfolioBuilder().build();
  }

  @Test(expected = Exception.class)
  public void invalidPortfolioNoShares() throws Exception {
    testPortfolio = new FlexiblePortfolioBuilder().createPortfolio("Hello").build();
  }

  @Test
  public void createdValidPortfolio() throws Exception {
    String testShare1 = "AAPL";
    int quantity1 = 1;
    testPortfolio =
            new FlexiblePortfolioBuilder()
                    .createPortfolio("Hello")
                    .addShares(testShare1, quantity1)
                    .build();
    String completedMessage = "No issues creating.";
    assertEquals("No issues creating.", completedMessage);
  }

  @Test
  public void getTotalValueAtDateBeforeCreationDate() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    String testDate = "2024-03-12";
    int quantity1 = 1;
    testPortfolio =
            new FlexiblePortfolioBuilder()
                    .createPortfolio(testPortfolioName)
                    .addShares(testShare1, quantity1)
                    .build();

    FetchApiInterface fetchApi = new FetchApi();
    String apiResponse1 = fetchApi.fetchData(testShare1, testDate);

    ApiProcessorInterface apiProcessor1 = new ApiProcessor(apiResponse1);

    double expectedOutput = (Double.parseDouble(apiProcessor1.getApiPrice()) * quantity1);
    String testOutput = testPortfolio.getTotalValueAtCertainDate("2024-03-12");
    assertEquals(testPortfolioName + " Value: $" + 0.0, testOutput);
  }

  @Test
  public void getTotalValueAtDateOnCreationDate() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    String testDate = LocalDate.now().toString();
    int quantity1 = 1;
    testPortfolio =
            new FlexiblePortfolioBuilder()
                    .createPortfolio(testPortfolioName)
                    .addShares(testShare1, quantity1)
                    .build();

    FetchApiInterface fetchApi = new FetchApi();
    String apiResponse1 = fetchApi.fetchData(testShare1, testDate);

    ApiProcessorInterface apiProcessor1 = new ApiProcessor(apiResponse1);

    double expectedOutput = (Double.parseDouble(apiProcessor1.getApiPrice()) * quantity1);
    String testOutput = testPortfolio.getTotalValueAtCertainDate(testDate);
    assertEquals(testPortfolioName + " Value: $" + expectedOutput, testOutput);
  }

  @Test
  public void getPortfolioComposition() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    int quantity1 = 1;
    testPortfolio =
            new FlexiblePortfolioBuilder()
                    .createPortfolio(testPortfolioName)
                    .addShares(testShare1, quantity1)
                    .build();
    String expectedOutput = testPortfolioName + "\n |--- ( AAPL, 1) \n";
    assertEquals(expectedOutput, testPortfolio.getPortfolioComposition());
  }

  @Test
  public void getAcceptBuy() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    int quantity1 = 1;
    String date = "2024-01-15";
    testPortfolio =
            new FlexiblePortfolioBuilder()
                    .createPortfolio(testPortfolioName)
                    .addShares(testShare1, quantity1)
                    .build();
    PortfolioVisitorModel p1 = new PortfolioBuyVisitor("AAPL", 5, date);
    testPortfolio.accept(p1);
    PortfolioVisitorModel p2 = new PortfolioBuyVisitor("MSFT", 5, date);
    testPortfolio.accept(p2);
    String expectedOutput = testPortfolioName + "\n |--- ( MSFT, 5) \n |--- ( AAPL, 6) \n";
    assertEquals(expectedOutput, testPortfolio.getPortfolioComposition());
  }

  @Test
  public void getAcceptSell() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    int quantity1 = 5;
    String date = LocalDate.now().toString();
    testPortfolio =
            new FlexiblePortfolioBuilder()
                    .createPortfolio(testPortfolioName)
                    .addShares(testShare1, quantity1)
                    .build();
    PortfolioVisitorModel p1 = new PortfolioSellVisitor("AAPL", 5, date);
    testPortfolio.accept(p1);
    String expectedOutput = testPortfolioName + "\n";
    assertEquals(expectedOutput, testPortfolio.getPortfolioComposition());
  }

  @Test
  public void getCostAtDate() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    String today = LocalDate.now().toString();
    int quantity1 = 1;
    testPortfolio =
            new FlexiblePortfolioBuilder()
                    .createPortfolio(testPortfolioName)
                    .addShares(testShare1, quantity1)
                    .build();

    FetchApiInterface fetchApi = new FetchApi();
    String apiResponse1 = fetchApi.fetchData(testShare1, today);

    ApiProcessorInterface apiProcessor1 = new ApiProcessor(apiResponse1);

    double expectedOutput = (Double.parseDouble(apiProcessor1.getApiPrice()) * quantity1);
    String testOutput = testPortfolio.getCostBasis("2024-03-12");
    assertEquals(testPortfolioName + " Cost-Basis: $" + 0.0, testOutput);
    testOutput = testPortfolio.getCostBasis(today);
    assertEquals(testPortfolioName + " Cost-Basis: $" + expectedOutput, testOutput);
    testOutput = testPortfolio.getCostBasis("2025-03-27");
    assertEquals(testPortfolioName + " Cost-Basis: $" + expectedOutput, testOutput);
  }

  @Test(expected = Exception.class)
  public void getAcceptSellTickerNotHere() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "MSFT";
    int quantity1 = 5;
    String date = "2024-01-15";
    testPortfolio =
            new FlexiblePortfolioBuilder()
                    .createPortfolio(testPortfolioName)
                    .addShares(testShare1, quantity1)
                    .build();
    PortfolioVisitorModel p1 = new PortfolioSellVisitor("AAPL", 5, date);
    testPortfolio.accept(p1);
  }

  @Test(expected = Exception.class)
  public void getAcceptSellNotEnoughShares() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    int quantity1 = 5;
    String date = "2024-01-15";
    testPortfolio =
            new FlexiblePortfolioBuilder()
                    .createPortfolio(testPortfolioName)
                    .addShares(testShare1, quantity1)
                    .build();
    PortfolioVisitorModel p1 = new PortfolioSellVisitor("AAPL", 6, date);
    testPortfolio.accept(p1);
  }
}
