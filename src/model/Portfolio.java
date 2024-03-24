package model;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Portfolio class represents a financial portfolio containing shares of
 * various ticker symbols.
 * It provides methods for managing the composition of the portfolio, including
 * adding and removing shares,
 * as well as calculating the total value of the portfolio based on current
 * market prices.
 */

class Portfolio implements PortfolioModel {
  String name;
  Map<String, List<ShareModel>> shares;

  /**
   * Constructs a new Portfolio object with the specified builder.
   *
   * @param newBuild The PortfolioBuilder used to build the portfolio.
   */
  private Portfolio(PortfolioBuilder newBuild) {
    this.name = newBuild.name;
    this.shares = newBuild.shares;
  }

  @Override
  public String getTotalValueAtCertainDate(String date) throws Exception {
    try {
      return this.name + " Value: $" + this.getPortfolioValue(date);
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public String getPortfolioComposition() {
    StringBuilder portComposition = new StringBuilder(this.name);
    portComposition.append("\n");
    for (Map.Entry<String, List<ShareModel>> entry : this.shares.entrySet()) {
      portComposition.append(" |--- ( ");
      portComposition.append(entry.getKey());
      portComposition.append(", ");
      portComposition.append(this.getTotalQuantityOfShare(entry.getValue()));
      portComposition.append(") \n");
    }
    return portComposition.toString();
  }

  // @Override
  // public CsvModel savePortfolio() {
  // return null;
  // }

  private int getTotalQuantityOfShare(List<ShareModel> listOfShares) {
    int quantity = 0;
    for (ShareModel groupOfSameShares : listOfShares) {
      quantity += groupOfSameShares.getQuantity();
    }
    return quantity;
  }

  private double getPortfolioValue(String date) throws Exception {
    double totalValue = 0;
    for (Map.Entry<String, List<ShareModel>> entry : this.shares.entrySet()) {
      for (ShareModel groupOfSameShares : entry.getValue()) {
        try {
          totalValue += groupOfSameShares.getValueAtDate(date);
        } catch (Exception e) {
          throw e;
        }

      }
    }
    return totalValue;
  }

  @Override
  public String savePortfolio() throws Exception {
    String filePath = System.getProperty("user.dir") + File.separator + "res" + File.separator;
    // csvFileIO.storeData(newEntry);
    CsvFileIOInterface csvFileIO = new CsvFileIO(this.name, filePath);

    for (Map.Entry<String, List<ShareModel>> entry : this.shares.entrySet()) {
      String[] newEntry = new String[6];

      for (ShareModel groupOfSameShares : entry.getValue()) {

        newEntry[0] = groupOfSameShares.getTickerSymbol();
        newEntry[1] = String.valueOf(groupOfSameShares.getQuantity());
        newEntry[2] = String.valueOf(groupOfSameShares.getCost());
        newEntry[3] = String.valueOf(groupOfSameShares.getCurrentValue());
        newEntry[4] = String.valueOf(groupOfSameShares.getCurrentValue()
                * groupOfSameShares.getQuantity());
        newEntry[5] = String.valueOf(groupOfSameShares.getCost() * groupOfSameShares.getQuantity());

      }
      csvFileIO.storeData(newEntry);
    }
    return "Successfully saved file in" + this.name + ".csv";
  }

  /**
   * The PortfolioBuilder class provides a fluent interface for building Portfolio
   * objects.
   */
  static class PortfolioBuilder {
    private String name;
    private Map<String, List<ShareModel>> shares;

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
    PortfolioModel build() throws Exception {
      if (this.shares.isEmpty()) {
        throw new Exception("Cannot create empty portfolio.");
      }
      return new Portfolio(this);
    }

    /**
     * Retrieves the shares in the portfolio.
     *
     * @return The map of ticker symbols to lists of ShareModel.
     */
    public Map<String, List<ShareModel>> getShares() {
      return shares;
    }
  }
}
