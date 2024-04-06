package model;

import java.io.File;
import java.time.LocalDate;

/**
 * The Share class represents a share in a portfolio. It provides methods to retrieve its cost,
 * current value based on market prices, and value at a specific date, offering functionality for
 * tracking and managing individual shares within a portfolio.
 */
class Share implements ShareModel {

  /** The ticker symbol of the share. */
  final String tickerSymbol;

  /** The date of the share. */
  String date;

  /** The CsvFileIOInterface for CSV file operations. */
  private final CsvFileIOInterface csvFileIO;

  /** The cost of the share. */
  protected double cost;

  /** Flag indicating whether cost is exact at the date. */
  boolean costAtExactDateFlag;

  /**
   * Constructs a new Share object with the specified ticker symbol.
   *
   * @param tickerSymbol The ticker symbol of the share.
   * @throws Exception if an error occurs while constructing the Share object.
   */
  Share(String tickerSymbol) throws Exception {
    validateTickerSymbol(tickerSymbol);
    this.tickerSymbol = tickerSymbol;
    this.date = String.valueOf(LocalDate.now());
    this.costAtExactDateFlag = false;
    String filePath = System.getProperty("user.dir") + File.separator + "res" + File.separator;
    this.csvFileIO = new CsvFileIO("transaction", filePath);
  }

  private void validateTickerSymbol(String tickerSymbol) throws Exception {
    String filePath = System.getProperty("user.dir") + File.separator + "res" + File.separator;
    CsvFileIOInterface csvFileIO = new CsvFileIO("symbols", filePath);
    CsvProcessorInterface csvProcessor = new CsvProcessor(csvFileIO.getData());
    if (csvProcessor.validateSymbol(tickerSymbol) == null) {
      throw new Exception(String.format("Error: %s is not a valid ticker symbol", tickerSymbol));
    }
  }

  @Override
  public double getCost() {
    return this.cost;
  }

  @Override
  public double getCostAtDate(String date){
    return 0.0;
  }

  @Override
  public double getCurrentValue() throws Exception {
    // if (cacheNode == null) {
    // throw new Exception("No data available");
    // }
    CsvProcessorInterface csvProcessor = new CsvProcessor(csvFileIO.getData());
    CacheNodeInterface cacheNode = csvProcessor.getData("SYMBOL");

    // CacheNode cacheNode = csvModel.getData(this.tickerSymbol);
    double cachePrice = -1;
    if (cacheNode != null) {
      String[] latestData = cacheNode.getData();
      cachePrice = Double.parseDouble(latestData[1]);
      this.costAtExactDateFlag = cacheNode.getPrecise();
    }

    FetchApiInterface fetchApi = new FetchApi();
    String apiResponse = fetchApi.fetchData(this.tickerSymbol);

    ApiProcessorInterface apiProcessor = new ApiProcessor(apiResponse);

    // ShareAPI api = new ShareAPI();
    // String[] latestRow = api.getLatestRow(this.tickerSymbol);
    double apiPrice = -1;
    if (apiResponse != null) {
      apiPrice = Double.parseDouble(apiProcessor.getApiPrice());
    }

    // if (latestRow==null){
    // return cachePrice;
    // }

    if (apiPrice == -1 && cachePrice == -1) {
      throw new Exception("API cannot provide data, and data not available in cache.");
    }

    if (apiPrice != cachePrice && apiPrice != -1) {
      return apiPrice;
    }

    return cachePrice;
  }

  @Override
  public double getValueAtDate(String date) throws Exception {
    CsvProcessorInterface csvProcessor = new CsvProcessor(csvFileIO.getData());
    CacheNodeInterface cacheNode = csvProcessor.getData(this.tickerSymbol, date);

    // CacheNode cacheNode = csvModel.getData(this.tickerSymbol, date);
    // String[] data1 = cacheNode.getData();

    // System.out.println(data1[1]);
    // System.out.println("data1[1]" + cacheNode.getData());
    if (cacheNode == null || !cacheNode.getPrecise()) {

      FetchApiInterface fetchApi = new FetchApi();
      String apiResponse = fetchApi.fetchData(this.tickerSymbol, date);

      ApiProcessorInterface apiProcessor = new ApiProcessor(apiResponse);

      // ShareAPI api = new ShareAPI();
      // String[] latestRow = api.getLatestRow(tickerSymbol, date);
      // apiPrice = Double.parseDouble(apiProcessor.getApiPrice());
      if (apiProcessor.getApiPrice() == null) {
        throw new Exception("Data is not available in both API and cached data");
      }
      String[] newEntry = {
        apiProcessor.getDate(),
        String.valueOf(Double.parseDouble(apiProcessor.getApiPrice())),
        apiProcessor.getSymbol()
      };
      csvFileIO.storeData(newEntry);
      return Double.parseDouble(apiProcessor.getApiPrice());
    }
    String[] data = cacheNode.getData();
    return Double.parseDouble(data[1]);
  }

  @Override
  public String getDate() {
    return this.date;
  }

  @Override
  public String getTickerSymbol() {
    return this.tickerSymbol;
  }

  @Override
  public double getQuantity() {
    return 1;
  }

  @Override
  public void setQuantity(double endQuantity) {
    endQuantity = 0;
  }
}
