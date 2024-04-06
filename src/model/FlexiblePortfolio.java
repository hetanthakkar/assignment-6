package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The FlexiblePortfolio class represents a financial portfolio that allows dynamic buying
 * and selling of shares.
 * It extends the AbstractPortfolio class and implements the FlexiblePortfolioModel interface,
 * providing methods for managing the composition of the portfolio, including adding and removing
 * shares, as well as calculating the total value of the portfolio based on current market prices.
 */
class FlexiblePortfolio extends AbstractPortfolio implements FlexiblePortfolioModel {

//  private final Map<String, Double> costBasisMap;

  /**
   * Constructs a new FlexiblePortfolio object with the specified builder.
   *
   * @param portfolioBuilder The PortfolioBuilder used to build the portfolio.
   */
  FlexiblePortfolio(PortfolioBuilder portfolioBuilder) {
    super(portfolioBuilder);
  }


  @Override
  public void buyShare(String tickerSymbol, double quantity, String date) throws Exception {
    ShareModel newShare;
    if (this.shares.containsKey(tickerSymbol)) {
      newShare = this.returnShareWithSameDate(tickerSymbol, date);
      if (newShare == null) {
        try {
          newShare = new PurchaseShares(tickerSymbol, quantity, date);
        } catch (Exception e) {
          throw e;
        }
        this.shares.get(tickerSymbol).add(newShare);
      } else {
        newShare.setQuantity(quantity + newShare.getQuantity());
      }
    } else {
      try {
        newShare = new PurchaseShares(tickerSymbol, quantity, date);
      } catch (Exception e) {
        throw e;
      }
      List<ShareModel> listOfShares = new ArrayList<>();
      listOfShares.add(newShare);
      this.shares.put(tickerSymbol, listOfShares);
    }
  }


  @Override
  public void sellShare(String share, double quantity, String date) throws Exception {
    if (!this.shares.containsKey(share)) {
      throw new Exception("Cannot sell stock that is not in the portfolio.");
    }
    if (!this.enoughSharesToSell(quantity, this.getTotalQuantityOfSpecificShareAtDate(share, date))) {
      throw new Exception(String.format("Not enough shares of %s to sell.", share));
    } else {
      this.removeSharesFromPortfolio(share, quantity, date);
    }
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
  public String getCostBasis(String date) {
    try {
      return this.name + " Cost-Basis: $" + getCostAtDate(date);
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public void accept(PortfolioVisitorModel visitor) throws Exception {
    visitor.visit(this);
  }

  protected double getPortfolioValue(String date) throws Exception {
    double totalValue = 0;
    for (Map.Entry<String, List<ShareModel>> entry : this.shares.entrySet()) {
      for (ShareModel groupOfSameShares : entry.getValue()) {
        try {
          if (isBeforeCreationDate(groupOfSameShares.getDate(), date)){
            totalValue += 0.0;
          }
          else {
            totalValue += groupOfSameShares.getValueAtDate(date);
          }
        } catch (Exception e) {
          throw e;
        }

      }
    }
    return totalValue;
  }

  private boolean isBeforeCreationDate(String dateOfShare, String dateOfGiven) {
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate givenDate = LocalDate.parse(dateOfGiven, inputFormatter);
    LocalDate shareDate = LocalDate.parse(dateOfShare, inputFormatter);
    return givenDate.isBefore(shareDate);
  }

  private double getTotalQuantityOfSpecificShareAtDate(String tickerSymbol, String date) {
    double quantity = 0.0;
    if (this.shares.containsKey(tickerSymbol)) {
      for (ShareModel s : this.shares.get(tickerSymbol)) {
        if (s.getDate().equals(date)){
          quantity = s.getQuantity();
        }
      }
    }
    return quantity;
  }

  private boolean enoughSharesToSell(double desiredSellAmount, double actualAmount) {
    return actualAmount - desiredSellAmount >= 0;
  }

  private void removeSharesFromPortfolio(String share, double desiredSellQuantity, String date) {
    ShareModel soldShare = null;
    for (ShareModel s : this.shares.get(share)){
      if (s.getDate().equals(date)){
        soldShare = s;
      }
    }
    if (soldShare != null){
      double resultShares = soldShare.getQuantity() - desiredSellQuantity;
      if (resultShares == 0.0){
        this.shares.get(share).remove(soldShare);
        if (this.shares.get(share).isEmpty()){
          this.shares.remove(share);
        }
      }
      else{
        soldShare.setQuantity(resultShares);
      }
    }
  }

  private ShareModel returnShareWithSameDate(String tickerSymbol, String date) {
    ShareModel shareSameDate = null;

    for (ShareModel s : this.shares.get(tickerSymbol)) {
      if (this.shareDateSameAsDesiredDate(s, date)) {
        shareSameDate = s;
        return shareSameDate;
      }
    }
    return null;
  }

  private boolean shareDateSameAsDesiredDate(ShareModel share, String desiredDate) {
    return share.getDate().equals(desiredDate);
  }

  protected double getCostAtDate(String date) {
    double totalCost = 0;
    for (Map.Entry<String, List<ShareModel>> entry : this.shares.entrySet()) {
      for (ShareModel groupOfSameShares : entry.getValue()) {
        try {
          totalCost += groupOfSameShares.getCostAtDate(date);
        } catch (Exception e) {
          throw e;
        }

      }
    }
    return totalCost;
  }

  /**
   * The FlexiblePortfolioBuilder class provides a fluent interface
   * for building FlexiblePortfolio objects.
   */
  public static class FlexiblePortfolioBuilder extends PortfolioBuilder {
    /**
     * Builds the portfolio.
     *
     * @return The constructed PortfolioModel.
     * @throws Exception if the portfolio is empty.
     */
    @Override
    PortfolioModel build() throws Exception {
      if (this.shares.isEmpty()) {
        throw new Exception("Cannot create empty portfolio.");
      }
      return new FlexiblePortfolio(this);
    }
  }

}
