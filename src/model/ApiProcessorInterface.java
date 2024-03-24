package model;

/**
 * The ApiProcessorInterface interface defines a contract for classes that
 * process API data.
 * It specifies methods for extracting and handling API responses, providing a
 * consistent
 * way to interact with and process data from various APIs.
 */

public interface ApiProcessorInterface {

  /**
   * Retrieves the date associated with the API data.
   *
   * @return The date associated with the API data.
   */
  String getDate();

  /**
   * Retrieves the price from the API data.
   *
   * @return The price from the API data.
   */
  String getApiPrice();

  /**
   * Retrieves the symbol associated with the API data.
   *
   * @return The symbol associated with the API data.
   */
  String getSymbol();
}
