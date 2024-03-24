package model;

/**
 * The ParsedShares class represents parsed share data. It implements the
 * ParsedSharesInterface
 * and provides methods for accessing and manipulating parsed share information.
 */

public class ParsedShares implements ParsedSharesInterface {
  private final String tickerSymbol;
  private final int quantity;

  /**
   * Constructs a new ParsedShares object with the specified ticker symbol and
   * quantity.
   *
   * @param tickerSymbol The ticker symbol of the parsed shares.
   * @param quantity     The quantity of parsed shares.
   */
  public ParsedShares(String tickerSymbol, int quantity) {
    this.tickerSymbol = tickerSymbol;
    this.quantity = quantity;
  }

  @Override
  public String getTickerSymbol() {
    return this.tickerSymbol;
  }

  @Override
  public int getQuantity() {
    return this.quantity;
  }
}