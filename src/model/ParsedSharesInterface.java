package model;

/**
 * The ParsedSharesInterface interface defines a contract for classes that
 * represent parsed shares.
 * It provides methods to retrieve the ticker symbol and quantity of parsed
 * shares, offering a
 * standardized way to work with parsed share data.
 */

public interface ParsedSharesInterface {

  /**
   * Retrieves the ticker symbol associated with the parsed shares.
   *
   * @return The ticker symbol of the parsed shares.
   */
  String getTickerSymbol();

  /**
   * Retrieves the quantity of parsed shares.
   *
   * @return The quantity of parsed shares.
   */
  int getQuantity();
}
