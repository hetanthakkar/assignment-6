package model;

/**
 * The ShareModel interface represents a share. It provides methods to retrieve
 * its cost, current value,
 * value at a specific date, as well as details like the date of purchase,
 * ticker symbol, and quantity,
 * offering a standardized way to work with share data within a portfolio.
 */

public interface ShareModel {

  /**
   * Retrieves the cost of the share.
   *
   * @return The cost of the share.
   */
  double getCost();

  /**
   * Retrieves the current value of the share.
   *
   * @return The current value of the share.
   * @throws Exception if there is an error while retrieving the current value.
   */
  double getCurrentValue() throws Exception;

  /**
   * Retrieves the value of the share at a specific date.
   *
   * @param date The date for which to retrieve the value of the share.
   * @return The value of the share on the specified date.
   * @throws Exception if there is an error while retrieving the value at the
   *                   specified date.
   */
  double getValueAtDate(String date) throws Exception;

  /**
   * Retrieves the date associated with the share.
   *
   * @return The date associated with the share.
   */
  String getDate();

  /**
   * Retrieves the ticker symbol of the share.
   *
   * @return The ticker symbol of the share.
   */
  String getTickerSymbol();

  /**
   * Retrieves the quantity of shares.
   *
   * @return The quantity of shares.
   */
  int getQuantity();
}
