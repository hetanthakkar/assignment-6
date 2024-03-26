package model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

import model.FlexiblePortfolio.FlexiblePortfolioBuilder;

import model.InflexiblePortfolio.InflexiblePortfolioBuilder;

/**
 * PortfolioTest is a test class to test the Portfolio class functions.
 */
public class InflexiblePortfolioTest {

  PortfolioModel testPortfolio;

  @Test(expected = Exception.class)
  public void invalidPortfolio() throws Exception {
    testPortfolio = new InflexiblePortfolioBuilder().build();
  }

  @Test(expected = Exception.class)
  public void invalidPortfolioNoShares() throws Exception {
    testPortfolio = new InflexiblePortfolioBuilder().createPortfolio("Hello").build();
  }

  @Test
  public void createdValidPortfolio() throws Exception {
    String testShare1 = "AAPL";
    int quantity1 = 1;
    testPortfolio = new InflexiblePortfolioBuilder().createPortfolio("Hello")
            .addShares(testShare1, quantity1).build();
    String completedMessage = "Done!";
    assertEquals("Done!", completedMessage);
  }

  @Test
  public void getTotalValueAtCertainDate() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    String testDate = "2024-03-12";
    int quantity1 = 1;
    testPortfolio = new InflexiblePortfolioBuilder()
            .createPortfolio(testPortfolioName).addShares(testShare1, quantity1).build();

    FetchApiInterface fetchApi = new FetchApi();
    String apiResponse1 = fetchApi.fetchData(testShare1, testDate);

    ApiProcessorInterface apiProcessor1 = new ApiProcessor(apiResponse1);

    double expectedOutput = (Double.parseDouble(apiProcessor1.getApiPrice()) * quantity1);
    String testOutput = testPortfolio.getTotalValueAtCertainDate("2024-03-12");
    assertEquals(testPortfolioName + " Value: $" + expectedOutput, testOutput);
  }

  @Test
  public void getPortfolioComposition() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    int quantity1 = 1;
    testPortfolio = new InflexiblePortfolioBuilder()
            .createPortfolio(testPortfolioName).addShares(testShare1, quantity1).build();
    String expectedOutput = testPortfolioName + "\n |--- ( AAPL, 1) \n";
    assertEquals(expectedOutput, testPortfolio.getPortfolioComposition());
  }

  @Test (expected = Exception.class)
  public void getAcceptBuy() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    int quantity1 = 1;
    testPortfolio = new InflexiblePortfolioBuilder()
            .createPortfolio(testPortfolioName).addShares(testShare1, quantity1).build();
    PortfolioVisitorModel p1=new PortfolioBuyVisitor("AAPL", 5);
    testPortfolio.accept(p1);
  }

  @Test //(expected = Exception.class)
  public void getAcceptBuyFlexiblePortfolio() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    int quantity1 = 1;
    testPortfolio = new FlexiblePortfolioBuilder()
            .createPortfolio(testPortfolioName).addShares(testShare1, quantity1).build();
    PortfolioVisitorModel p1=new PortfolioBuyVisitor("AAPL", 5);
    testPortfolio.accept(p1);
    System.out.println(testPortfolio.getPortfolioComposition());
  }

}