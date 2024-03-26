package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

abstract class PortfolioBuilder {
  protected String name;
  protected Map<String, List<ShareModel>> shares;

  /**
   * Creates a new PortfolioBuilder with the specified portfolio name.
   *
   * @param name The name of the portfolio.
   * @return The PortfolioBuilder instance.
   * @throws IllegalArgumentException if the portfolio name contains
   *                                  non-alphanumeric characters.
   */
  PortfolioBuilder createPortfolio(String name) {
    if (!name.matches("[A-Za-z0-9]+")) {
      throw new IllegalArgumentException("Portfolio" +
              "name must only contain alphanumeric characters.");
    }
    this.name = name;
    this.shares = new HashMap<>();
    return this;
  }

  /**
   * Adds shares to the portfolio.
   *
   * @param tickerSymbol The ticker symbol of the shares to add.
   * @param quantity     The quantity of shares to add.
   * @return The PortfolioBuilder instance.
   * @throws Exception if an error occurs while adding shares.
   */
  PortfolioBuilder addShares(String tickerSymbol, int quantity) throws Exception {
    ShareModel newShare;
    try {
      newShare = new PurchaseShares(tickerSymbol, quantity);
    } catch (Exception e) {
      throw e;
    }
    if (this.shares.containsKey(tickerSymbol)) {
      List<ShareModel> existingListShares = this.shares.get(tickerSymbol);
      existingListShares.add(newShare);
    } else {
      List<ShareModel> listOfShares = new ArrayList<>();
      listOfShares.add(newShare);
      this.shares.put(tickerSymbol, listOfShares);
    }
    return this;
  }

  /**
   * Builds the portfolio.
   *
   * @return The constructed PortfolioModel.
   * @throws Exception if the portfolio is empty.
   */
  abstract PortfolioModel build() throws Exception;

}
