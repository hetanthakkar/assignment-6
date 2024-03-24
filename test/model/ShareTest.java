package model;

import org.junit.Test;

import java.time.LocalDate;

import static org.junit.Assert.assertEquals;

/**
 * ShareTest is a test class to test Share class functionality.
 */
public class ShareTest {

  ShareModel testShare;

  @Test(expected = Exception.class)
  public void invalidTickerError() throws Exception {
    String testShareName = "asdf";
    testShare = new Share(testShareName);

  }

  @Test
  public void getCurrentValue() throws Exception {
    String testShareName = "AAPL";

    testShare = new Share(testShareName);
    Double actual = testShare.getCurrentValue();

    FetchApiInterface fetchApi = new FetchApi();
    String apiResponse1 = fetchApi.fetchData(testShareName);

    ApiProcessorInterface apiProcessor1 = new ApiProcessor(apiResponse1);

    Double expectedOutput = (Double.parseDouble(apiProcessor1.getApiPrice()));
    assertEquals(expectedOutput, actual, 0.009);
  }

  @Test
  public void getValueAtDate() throws Exception {
    String testShareName = "AAPL";
    String testDate = "2024-03-11";

    testShare = new Share(testShareName);
    Double actual = testShare.getValueAtDate(testDate);

    FetchApiInterface fetchApi = new FetchApi();
    String apiResponse1 = fetchApi.fetchData(testShareName, testDate);

    ApiProcessorInterface apiProcessor1 = new ApiProcessor(apiResponse1);

    Double expectedOutput = (Double.parseDouble(apiProcessor1.getApiPrice()));
    assertEquals(expectedOutput, actual, 0.009);
  }

  @Test
  public void getDate() throws Exception {
    String testShareName = "AAPL";
    String currentDate = LocalDate.now().toString();

    testShare = new Share(testShareName);

    assertEquals(currentDate, testShare.getDate());
  }

  @Test
  public void getTickerSymbol() throws Exception {
    String testShareName = "AAPL";

    testShare = new Share(testShareName);

    assertEquals(testShareName, testShare.getTickerSymbol());
  }

}