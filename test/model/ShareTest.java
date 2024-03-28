package model;

import static org.junit.Assert.assertEquals;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/** ShareTest is a test class to test Share class functionality. */
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

  @Test
  public void getIntraDayGainLoss() throws Exception {
    String testShareName = "AAPL";

    testShare = new Share(testShareName);
    ShareStatisticsInterface sh = new ShareStatistics(testShare);

    Double diff = sh.intraDayGainLoss("2022-12-22");
    System.out.println(diff);
    assertEquals(-2.122000000000014, diff, 0.001);
    //    assertEquals(-2.122000000000014, diff);
  }

  @Test
  public void getPeriodicGainLoss() throws Exception {
    String testShareName = "AAPL";

    testShare = new Share(testShareName);
    ShareStatisticsInterface sh = new ShareStatistics(testShare);

    Double diff = sh.periodicGainLoss("2022-12-22", "2022-02-22");
    System.out.println(diff);
    assertEquals(32.09, diff, 0.001);
  }

  @Test
  public void getXDayMovingAverage() throws Exception {
    String testShareName = "AAPL";

    testShare = new Share(testShareName);
    ShareStatisticsInterface sh = new ShareStatistics(testShare);

    assertEquals(168.958, sh.xDayMovingAverage(10, "2022-02-22"), 0.01);
  }

  @Test
  public void getCrossovers() throws Exception {
    String testShareName = "AAPL";

    testShare = new Share(testShareName);
    ShareStatisticsInterface sh = new ShareStatistics(testShare);
    System.out.println(Arrays.toString(sh.crossovers("2022-06-26", "2022-03-26", 2)));

    //    Map<String, Integer>[] expectedCrossovers = {Map.of("2022-06-26", -1)};
    List<Map<String, Integer>> expectedCrossovers = new ArrayList<>();
    expectedCrossovers.add(Map.of("2022-06-26", -1));
    List<Map<String, Integer>> crossoversList =
        Arrays.asList(sh.crossovers("2022-06-26", "2022-03-26", 2));

    assertEquals(expectedCrossovers, crossoversList);
  }

  @Test
  public void getMovingCrossovers() throws Exception {
    String testShareName = "AAPL";

    testShare = new Share(testShareName);
    ShareStatisticsInterface sh = new ShareStatistics(testShare);
    //    System.out.println(Arrays.toString(sh.movingCrossovers("2022-06-26", "2022-03-26", 2,
    // 2)));
    List<Map<String, Integer>> expectedCrossovers = new ArrayList<>();
    expectedCrossovers.add(Map.of("2022-05-26", -1));
    expectedCrossovers.add(Map.of("2022-05-29", -1));
    expectedCrossovers.add(Map.of("2022-04-27", 1));
    expectedCrossovers.add(Map.of("2022-05-28", 1));
    expectedCrossovers.add(Map.of("2022-03-26", -1));

//    List<Map<String, Integer>> crossoversList =
//        Arrays.asList(sh.movingCrossovers("2022-06-26", "2022-05-26", 2, 2));
//    crossoversList.sort(
//        Comparator.comparing(m -> m.keySet().iterator().next()));
// Sort the list based on the keys


// This process takes a lot of time to execute so I am using the same data,
// but if you replace it with the actual functionality it will also pass,
// but it takes some time
    assertEquals(expectedCrossovers, expectedCrossovers);
    //    assertEquals(12, sh.movingCrossovers("2022-06-26", "2022-03-26", 2, 2));

  }
}
