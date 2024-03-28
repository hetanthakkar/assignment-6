package model;

import java.io.File;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

abstract class AbstractPortfolio implements  PortfolioModel {
  String name;
  Map<String, List<ShareModel>> shares;

  String creationDate;

  /**
   * Constructs a new Portfolio object with the specified builder.
   *
   * @param newBuild The PortfolioBuilder used to build the portfolio.
   */
  AbstractPortfolio(PortfolioBuilder newBuild) {
    this.name = newBuild.name;
    this.shares = newBuild.shares;
    this.creationDate = LocalDate.now().toString();
  }

  @Override
  public String getTotalValueAtCertainDate(String date) throws Exception {
    try {
      return this.name + " Value: $" + getPortfolioValue(date);
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

  protected double getPortfolioValue(String date) throws Exception {
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

  @Override
  public abstract void accept(PortfolioVisitorModel visitor) throws Exception;

  @Override
  public String getCostBasis(String date){
    try {
      return this.name + " Cost-Basis: $" + getCostBasisValue();
    } catch (Exception e) {
      throw e;
    }
  }

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
