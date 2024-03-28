package model;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class ShareStatistics implements ShareStatisticsInterface {

  ShareModel share;

  public ShareStatistics(ShareModel share) {
    this.share = share;
    String filePath = System.getProperty("user.dir") + File.separator;
    this.csvFileIO = new CsvFileIO("transaction", filePath);
  }

  private final CsvFileIOInterface csvFileIO;

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

  @Override
  public Double intraDayGainLoss(String date) throws Exception {
    Double opening = getOpeningPrice(date);
    Double closing = share.getValueAtDate(date);
    return closing - opening;
  }

  @Override
  public Double periodicGainLoss(String date1, String date2) throws Exception {
    Double closing1 = share.getValueAtDate(date1);
    Double closing2 = share.getValueAtDate(date2);
    return closing2 - closing1;
  }

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

  @Override
  public Map[] crossovers(String date1, String date2, int X) throws Exception {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate startDate = LocalDate.parse(date1, formatter);
    LocalDate endDate = LocalDate.parse(date2, formatter);

    Map<String, Integer> crossoverMap = new HashMap<>();
    LocalDate currentDate = startDate;
    double prevMovingAvg = xDayMovingAverage(X, currentDate.format(formatter));
    double prevPrice = share.getValueAtDate(currentDate.format(formatter));
    int direction = prevPrice > prevMovingAvg ? 1 : -1;
    crossoverMap.put(currentDate.format(formatter), direction);

    currentDate = currentDate.plusDays(1);

    while (!currentDate.isAfter(endDate)) {
      double movingAvg = xDayMovingAverage(X, currentDate.format(formatter));
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

  @Override
  public Map[] crossovers(String date1, String date2) throws Exception {
    return crossovers(date1, date2, 30);
  }

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

  @Override
  public Map[] movingCrossovers(String date1, String date) throws Exception {
    return this.movingCrossovers(date1, date, 3, 1);
  }
}
