package model;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * The AbstractPortfolio class is an abstract class that implements the PortfolioModel interface.
 * It provides common functionality for portfolio objects.
 */
abstract class AbstractPortfolio implements PortfolioModel {

  /** The name of the portfolio. */
  protected String name;

  /** A map representing the shares held in the portfolio. */
  protected Map<String, List<ShareModel>> shares;

  /** The date of creation of the portfolio. */
  protected String creationDate;

  /**
   * Constructs a new AbstractPortfolio object with the specified builder.
   *
   * @param newBuild The PortfolioBuilder used to build the portfolio.
   */
  AbstractPortfolio(PortfolioBuilder newBuild) {
    this.name = newBuild.name;
    this.shares = newBuild.shares;
    this.creationDate = LocalDate.now().toString();
  }

  /**
   * Retrieves the total value of the portfolio at a certain date.
   *
   * @param date the date for which the total value is requested
   * @return a string representing the total value of the portfolio
   * @throws Exception if an error occurs while retrieving the total value
   */
  @Override
  public String getTotalValueAtCertainDate(String date) throws Exception {
    try {
      return this.name + " Value: $" + getPortfolioValue(date);
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Retrieves the composition of the portfolio.
   *
   * @return a string representing the composition of the portfolio
   */
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

  /**
   * Retrieves the total quantity of shares for a given list of shares.
   *
   * @param listOfShares the list of shares
   * @return the total quantity of shares
   */
  private int getTotalQuantityOfShare(List<ShareModel> listOfShares) {
    int quantity = 0;
    for (ShareModel groupOfSameShares : listOfShares) {
      quantity += groupOfSameShares.getQuantity();
    }
    return quantity;
  }

  /**
   * Retrieves the total value of the portfolio at a certain date.
   *
   * @param date the date for which the total value is requested
   * @return the total value of the portfolio
   * @throws Exception if an error occurs while retrieving the total value
   */
  protected double getPortfolioValue(String date) throws Exception {
    double totalValue = 0;
    if (date.equals(this.creationDate)){
      return getCostBasisValue();
    }
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

  /**
   * Saves the portfolio to a CSV file.
   *
   * @return a message indicating the success of the saving operation
   * @throws Exception if an error occurs while saving the portfolio
   */
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
   * Accepts a visitor for the portfolio.
   *
   * @param visitor the visitor to accept
   * @throws Exception if an error occurs while accepting the visitor
   */
  @Override
  public abstract void accept(PortfolioVisitorModel visitor) throws Exception;

  /**
   * Retrieves the cost basis of the portfolio.
   *
   * @param date the date for which the cost basis is requested
   * @return a string representing the cost basis of the portfolio
   */
  @Override
  public String getCostBasis(String date){
    try {
      return this.name + " Cost-Basis: $" + getCostBasisValue();
    } catch (Exception e) {
      throw e;
    }
  }

  /**
   * Retrieves the cost basis value of the portfolio.
   *
   * @return the cost basis value of the portfolio
   */
  protected double getCostBasisValue(){
    double cost = 0;
    for (Map.Entry<String, List<ShareModel>> entry : this.shares.entrySet()) {
      for (ShareModel groupOfSameShares : entry.getValue()) {
        cost += groupOfSameShares.getCost();
      }
    }
    return cost;
  }
}

