package model;

import org.junit.Test;

import java.time.LocalDate;

import model.InflexiblePortfolio.InflexiblePortfolioBuilder;

import static org.junit.Assert.assertEquals;

/**
 * PortfolioTest is a test class to test the Portfolio class functions.
 */
public class InflexiblePortfolioTest extends AbstractPortfolioTest {

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
    testPortfolio =
            new InflexiblePortfolioBuilder()
                    .createPortfolio("Hello")
                    .addShares(testShare1, quantity1)
                    .build();
    String completedMessage = "No issues creating.";
    assertEquals("No issues creating.", completedMessage);
  }

  @Test
  public void getTotalValueAtCertainDate() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    String testDate = "2024-03-12";
    int quantity1 = 1;
    testPortfolio =
            new InflexiblePortfolioBuilder()
                    .createPortfolio(testPortfolioName)
                    .addShares(testShare1, quantity1)
                    .build();

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
    testPortfolio =
            new InflexiblePortfolioBuilder()
                    .createPortfolio(testPortfolioName)
                    .addShares(testShare1, quantity1)
                    .build();
    String expectedOutput = testPortfolioName + "\n |--- ( AAPL, 1) \n";
    assertEquals(expectedOutput, testPortfolio.getPortfolioComposition());
  }

  @Test(expected = Exception.class)
  public void getAcceptBuy() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    String date = "2024-01-15";
    int quantity1 = 1;
    testPortfolio =
            new InflexiblePortfolioBuilder()
                    .createPortfolio(testPortfolioName)
                    .addShares(testShare1, quantity1)
                    .build();
    PortfolioVisitorModel p1 = new PortfolioBuyVisitor("AAPL", 5, date);
    testPortfolio.accept(p1);
  }

  @Test(expected = Exception.class)
  public void getAcceptSell() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    int quantity1 = 5;
    String date = "2024-01-15";
    testPortfolio =
            new InflexiblePortfolioBuilder()
                    .createPortfolio(testPortfolioName)
                    .addShares(testShare1, quantity1)
                    .build();
    PortfolioVisitorModel p1 = new PortfolioSellVisitor("AAPL", 3, date);
    testPortfolio.accept(p1);
  }

  @Test
  public void getCostAtDate() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    String today = LocalDate.now().toString();
    int quantity1 = 1;
    testPortfolio =
            new InflexiblePortfolioBuilder()
                    .createPortfolio(testPortfolioName)
                    .addShares(testShare1, quantity1)
                    .build();

    FetchApiInterface fetchApi = new FetchApi();
    String apiResponse1 = fetchApi.fetchData(testShare1, today);

    ApiProcessorInterface apiProcessor1 = new ApiProcessor(apiResponse1);

    double expectedOutput = (Double.parseDouble(apiProcessor1.getApiPrice()) * quantity1);
    String testOutput = testPortfolio.getCostBasis("2024-03-12");
    assertEquals(testPortfolioName + " Cost-Basis: $" + expectedOutput, testOutput);
    testOutput = testPortfolio.getCostBasis("2024-03-27");
    assertEquals(testPortfolioName + " Cost-Basis: $" + expectedOutput, testOutput);
    testOutput = testPortfolio.getCostBasis("2025-03-27");
    assertEquals(testPortfolioName + " Cost-Basis: $" + expectedOutput, testOutput);
  }

  @Test
  public void getBarChart() throws Exception {
    String testPortfolioName = "Hello";
    String testShare1 = "AAPL";
    int quantity1 = 5;
    testPortfolio =
            new InflexiblePortfolioBuilder()
                    .createPortfolio(testPortfolioName)
                    .addShares(testShare1, quantity1)
                    .build();
    String chart = testPortfolio.generatePerformanceBarChart("2019-01-22", "2022-12-22");

    String expectedOutput =
            "\n"
                    + "Performance of portfolio Hello from 2019-01-22 to 2022-12-22\n\n"
                    + "January 2019 22   : *******************\n"
                    + "March 2019 12     : ***********************\n"
                    + "May 2019 01       : ***************************\n"
                    + "June 2019 19      : *************************\n"
                    + "August 2019 07    : *************************\n"
                    + "September 2019 26 : ****************************\n"
                    + "November 2019 14  : **********************************\n"
                    + "January 2020 02   : **************************************\n"
                    + "February 2020 21  : ****************************************\n"
                    + "April 2020 10     : **********************************\n"
                    + "May 2020 29       : *****************************************\n"
                    + "July 2020 18      : **************************************************\n"
                    + "September 2020 05 : ***************\n"
                    + "October 2020 24   : **************\n"
                    + "December 2020 13  : ***************\n"
                    + "January 2021 31   : *****************\n"
                    + "March 2021 22     : ****************\n"
                    + "May 2021 10       : ****************\n"
                    + "June 2021 28      : *****************\n"
                    + "August 2021 17    : *******************\n"
                    + "October 2021 05   : ******************\n"
                    + "November 2021 23  : ********************\n"
                    + "January 2022 12   : **********************\n"
                    + "March 2022 02     : *********************\n"
                    + "April 2022 20     : *********************\n"
                    + "June 2022 09      : ******************\n"
                    + "July 2022 28      : ********************\n"
                    + "September 2022 15 : *******************\n"
                    + "November 2022 04  : *****************\n"
                    + "\n"
                    + "Absolute Scale: * = 38.531\n";
    //    System.out.println(s1);
    //    String expectedOutput = testPortfolioName + "\n";
    assertEquals(
            expectedOutput, testPortfolio.generatePerformanceBarChart("2019-01-22", "2022-12-22"));
  }
}
