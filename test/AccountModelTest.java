//import model.ApiProcessor;
//import model.FetchApi;
//import org.junit.Test;
//
//import static org.junit.Assert.assertEquals;
//import model.AccountModel;
//import model.Account;
//
///**
// * AccountModelTest is a test class that checks functions of AccountModel objects are working.
// */
//public class AccountModelTest {
//
//  AccountModel newAccount = new Account();
//
//  @Test
//  public void addShare() throws Exception {
//    newAccount.setPortfolioName("Alex");
//    newAccount.addShare("AAPL", 5);
//    newAccount.finishBuild();
//    String completeMessage = "Done!";
//    assertEquals("Done!", completeMessage);
//  }
//
//  @Test
//  public void getPortfolioComposition() throws Exception {
//    newAccount.setPortfolioName("Hello");
//    newAccount.addShare("AAPL", 5);
//    newAccount.addShare("GOOG", 5);
//    newAccount.finishBuild();
//    String expectedOutput = "Hello\n |--- ( GOOG, 5) \n |--- ( AAPL, 5) \n";
//    assertEquals(expectedOutput, newAccount.getPortfolioComposition("Hello"));
//  }
//
//  @Test
//  public void getPortfolioValue() throws Exception {
//    String testPortfolioName = "Alex1";
//    String testStock1 = "AMZN";
//    int quantity1 = 1;
//    String testStock2 = "GOOG";
//    int quantity2 = 1;
//    String testDate = "2024-03-12";
//    newAccount.setPortfolioName(testPortfolioName);
//    newAccount.addShare(testStock1, quantity1);
//    newAccount.addShare(testStock2, quantity2);
//    newAccount.finishBuild();
//
//    FetchApi fetchApi = new FetchApi();
//    String apiResponse1 = fetchApi.fetchData(testStock1, testDate);
//    String apiResponse2 = fetchApi.fetchData(testStock2, testDate);
//
//    ApiProcessor apiProcessor1 = new ApiProcessor(apiResponse1);
//    ApiProcessor apiProcessor2 = new ApiProcessor(apiResponse2);
//
//    double expectedOutput = ((Double.parseDouble(apiProcessor1.getApiPrice()) * quantity1)
//        + (Double.parseDouble(apiProcessor2.getApiPrice()) * quantity2));
//    String testOutput = newAccount.getPortfolioTotalValueAtCertainDate(testPortfolioName, testDate);
//    assertEquals(testPortfolioName + " Value: $" + expectedOutput, testOutput);
//
//  }
//
//  @Test(expected = Exception.class)
//  public void getPortfolioValueFutureDateGetErrorMessage() throws Exception {
//    String testPortfolioName = "Alex2";
//    String testStock1 = "AMZN";
//    int quantity1 = 1;
//    String testStock2 = "TSLA";
//    int quantity2 = 1;
//    newAccount.setPortfolioName(testPortfolioName);
//    newAccount.addShare(testStock1, quantity1);
//    newAccount.addShare(testStock2, quantity2);
//    newAccount.finishBuild();
//    String testOutput = newAccount.getPortfolioTotalValueAtCertainDate(testPortfolioName,
//            "2025-03-03");
//  }
//
//  @Test(expected = Exception.class)
//  public void createEmptyPortfolio() throws Exception {
//    String portName = "EmptyPort";
//    newAccount.setPortfolioName(portName);
//    newAccount.finishBuild();
//  }
//
//}