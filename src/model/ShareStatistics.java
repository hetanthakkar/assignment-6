package model;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * The ShareStatistics class provides various statistical calculations and analyses
 * related to a ShareModel object representing a stock or security.
 */
public class ShareStatistics implements ShareStatisticsInterface {
  private final ShareModel share;
  private final CsvFileIOInterface csvFileIO;

  /**
   * Constructs a ShareStatistics object with the given ShareModel.
   *
   * @param share The ShareModel object representing the stock or security.
   */
  public ShareStatistics(ShareModel share) {
    this.share = share;
    String filePath = System.getProperty("user.dir") + File.separator;
    this.csvFileIO = new CsvFileIO("transaction", filePath);
  }

  /**
   * Retrieves the opening price for the given date.
   *
   * @param date The date in the format "yyyy-MM-dd" for which the opening price is needed.
   * @return The opening price for the specified date.
   * @throws Exception If the opening price data is not available in both the API and cached data.
   */
  private Double getOpeningPrice(String date) throws Exception {
    CsvProcessorInterface csvProcessor = new CsvProcessor(csvFileIO.getData());
    CacheNodeInterface cacheNode = csvProcessor.getData(share.getTickerSymbol(), date);
    if (cacheNode == null || !cacheNode.getPrecise()) {
      FetchApiInterface fetchApi = new FetchApi();
      String apiResponse = fetchApi.fetchData(share.getTickerSymbol(), date);
      ApiProcessorInterface apiProcessor = new ApiProcessor(apiResponse);
      if (apiProcessor.getApiPrice() == null) {
        throw new Exception("Data is not available in both API and cached data");
      }
      String[] newEntry = {
              apiProcessor.getDate(),
              String.valueOf(Double.parseDouble(apiProcessor.getApiOpeningPrice())),
              apiProcessor.getSymbol()
      };
      csvFileIO.storeData(newEntry);
      return Double.parseDouble(apiProcessor.getApiOpeningPrice());
    }
    String[] data = cacheNode.getData();
    return Double.parseDouble(data[1]);
  }

  /**
   * Calculates the intra-day gain or loss for the given date.
   *
   * @param date The date in the format "yyyy-MM-dd" for which the intra-day gain/loss is needed.
   * @return The intra-day gain or loss, calculated as the closing price minus the opening price.
   * @throws Exception If the opening price data is not available.
   */
  @Override
  public Double intraDayGainLoss(String date) throws Exception {
    Double opening = getOpeningPrice(date);
    Double closing = share.getValueAtDate(date);
    return closing - opening;
  }

  /**
   * Calculates the periodic gain or loss between two dates.
   *
   * @param date1 The start date in the format "yyyy-MM-dd".
   * @param date2 The end date in the format "yyyy-MM-dd".
   * @return The periodic gain or loss, calculated as the closing price on
   *     date2 minus the closing price on date1.
   * @throws Exception If the closing price data is not available for either date.
   */
  @Override
  public Double periodicGainLoss(String date1, String date2) throws Exception {
    Double closing1 = share.getValueAtDate(date1);
    Double closing2 = share.getValueAtDate(date2);
    return closing2 - closing1;
  }

  /**
   * Calculates the X-day moving average for the given date.
   *
   * @param x    The number of days for the moving average calculation.
   * @param date The date in the format "yyyy-MM-dd" for which the moving average is needed.
   * @return The X-day moving average for the specified date.
   * @throws Exception If the closing price data is not available for any of the required dates.
   */
  @Override
  public Double xDayMovingAverage(int x, String date) throws Exception {
    double sum = 0;
    for (int i = 0; i < x; i++) {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
      LocalDate parsedDate = LocalDate.parse(date, formatter);
      LocalDate thirtyDaysBeforeDate = parsedDate.minusDays(x - i);
      sum = sum + share.getValueAtDate(thirtyDaysBeforeDate.format(formatter));
    }
    return sum / x;
  }

  /**
   * Calculates the crossovers between the stock price and its X-day moving average
   * for the given date range.
   *
   * @param date1 The start date in the format "yyyy-MM-dd".
   * @param date2 The end date in the format "yyyy-MM-dd".
   * @param x     The number of days for the moving average calculation.
   * @return A map containing the dates and corresponding crossover directions (1 or -1).
   * @throws Exception If the required data is not available for any of the dates in the range.
   */
  @Override
  public Map[] crossovers(String date1, String date2, int x) throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate startDate = LocalDate.parse(date1, formatter);
    LocalDate endDate = LocalDate.parse(date2, formatter);
    Map<String, Integer> crossoverMap = new HashMap<>();
    LocalDate currentDate = startDate;
    double prevMovingAvg = xDayMovingAverage(x, currentDate.format(formatter));
    double prevPrice = share.getValueAtDate(currentDate.format(formatter));
    int direction = prevPrice > prevMovingAvg ? 1 : -1;
    crossoverMap.put(currentDate.format(formatter), direction);
    currentDate = currentDate.plusDays(1);
    while (!currentDate.isAfter(endDate)) {
      double movingAvg = xDayMovingAverage(x, currentDate.format(formatter));
      double price = share.getValueAtDate(currentDate.format(formatter));
      int newDirection = price > movingAvg ? 1 : -1;
      if (newDirection != direction) {
        crossoverMap.put(currentDate.format(formatter), newDirection);
        direction = newDirection;
      }
      currentDate = currentDate.plusDays(1);
    }
    return new Map[] {crossoverMap};
  }

  /**
   * Calculates the crossovers between the stock price and its 30-day moving average
   * for the given date range.
   *
   * @param date1 The start date in the format "yyyy-MM-dd".
   * @param date2 The end date in the format "yyyy-MM-dd".
   * @return A map containing the dates and corresponding crossover directions (1 or -1).
   * @throws Exception If the required data is not available for any of the dates in the range.
   */
  @Override
  public Map[] crossovers(String date1, String date2) throws Exception {
    return crossovers(date1, date2, 30);
  }

  /**
   * Calculates the crossovers between two moving averages (X-day and Y-day)
   * for the given date range.
   *
   * @param date1 The start date in the format "yyyy-MM-dd".
   * @param date2 The end date in the format "yyyy-MM-dd".
   * @param x     The number of days for the first moving average calculation.
   * @param y     The number of days for the second moving average calculation.
   * @return A map containing the dates and corresponding crossover directions (1 or -1).
   * @throws Exception If the required data is not available for any of the dates in the range.
   */
  @Override
  public Map[] movingCrossovers(String date1, String date2, int x, int y) throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate startDate = LocalDate.parse(date1, formatter);
    LocalDate endDate = LocalDate.parse(date2, formatter);
    if (startDate.isAfter(endDate)) {
      LocalDate temp = startDate;
      startDate = endDate;
      endDate = temp;
    }
    Map<String, Integer> crossoverMap = new HashMap<>();
    LocalDate currentDate = startDate;
    double prevXDayAvg = xDayMovingAverage(x, currentDate.format(formatter));
    double prevYDayAvg = xDayMovingAverage(y, currentDate.format(formatter));
    int direction = prevXDayAvg > prevYDayAvg ? 1 : -1;
    crossoverMap.put(currentDate.format(formatter), direction);
    currentDate = currentDate.plusDays(1);
    while (!currentDate.isAfter(endDate)) {
      double xDayAvg = xDayMovingAverage(x, currentDate.format(formatter));
      double yDayAvg = xDayMovingAverage(y, currentDate.format(formatter));
      int newDirection = xDayAvg > yDayAvg ? 1 : -1;
      if (newDirection != direction) {
        crossoverMap.put(currentDate.format(formatter), newDirection);
        direction = newDirection;
      }
      currentDate = currentDate.plusDays(1);
    }
    return new Map[] {crossoverMap};
  }

  /**
   * Calculates the crossovers between the 3-day and 1-day moving averages
   * for the given date range.
   *
   * @param date1 The start date in the format "yyyy-MM-dd".
   * @param date2 The end date in the format "yyyy-MM-dd".
   * @return A map containing the dates and corresponding crossover directions (1 or -1).
   * @throws Exception If the required data is not available for any of the dates in the range.
   */
  @Override
  public Map[] movingCrossovers(String date1, String date2) throws Exception {
    return this.movingCrossovers(date1, date2, 3, 1);
  }
}