package model;

/**
 * The ApiProcessor class handles the processing of API responses, extracting
 * important information
 * such as date, price, and symbol data. It provides functionality to parse and
 * interpret raw API
 * responses into structured data that can be used for further analysis or
 * display.
 */

public class ApiProcessor implements ApiProcessorInterface {

  private String date;
  private String apiPrice;
  private String symbol;

  /**
   * Constructs a new ApiProcessor object with the provided API response.
   *
   * @param apiResponse The API response to be processed.
   */
  public ApiProcessor(String apiResponse) {
    if (apiResponse != null) {
      String[] temp = apiResponse.split(",");
      if (temp.length == 6) {
        this.date = temp[0];
        this.apiPrice = temp[temp.length - 2];
        this.symbol = temp[temp.length - 1];
      }
    }
  }

  @Override
  public String getDate() {
    return date;
  }

  @Override
  public String getApiPrice() {
    return apiPrice;
  }

  @Override
  public String getSymbol() {
    return symbol;
  }
}
