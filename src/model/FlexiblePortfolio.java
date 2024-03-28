package model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

class FlexiblePortfolio extends AbstractPortfolio implements FlexiblePortfolioModel {

  private Map<String, Double> costBasisMap;

  FlexiblePortfolio(PortfolioBuilder portfolioBuilder) {
    super(portfolioBuilder);
    this.costBasisMap = new TreeMap<String, Double>();
    this.updateCostMap();
  }


  @Override
  public void buyShare(String tickerSymbol, int quantity) throws Exception {
    ShareModel newShare;
    if (this.shares.containsKey(tickerSymbol)) {
      newShare = this.returnShareWithSameDate(tickerSymbol, LocalDate.now().toString());
      if (newShare == null) {
        try {
          newShare = new PurchaseShares(tickerSymbol, quantity);
        } catch (Exception e) {
          throw e;
        }
        this.shares.get(tickerSymbol).add(newShare);
      } else {
        newShare.setQuantity(quantity + newShare.getQuantity());
      }
    } else {
      try {
        newShare = new PurchaseShares(tickerSymbol, quantity);
      } catch (Exception e) {
        throw e;
      }
      List<ShareModel> listOfShares = new ArrayList<>();
      listOfShares.add(newShare);
      this.shares.put(tickerSymbol, listOfShares);
    }
    this.updateCostMap();
  }


  @Override
  public void sellShare(String share, int quantity) throws Exception {
    if (!this.shares.containsKey(share)) {
      throw new Exception("Cannot sell stock that is not in the portfolio.");
    }
    if (!this.enoughSharesToSell(quantity,this.getTotalQuantityOfSpecificShare(share))) {
      throw new Exception(String.format("Not enough shares of %s to sell.", share));
    }
    else {
      this.removeSharesFromPortfolio(share, quantity);
    }
    this.updateCostMap();
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

  public static class FlexiblePortfolioBuilder extends PortfolioBuilder {

    @Override
    PortfolioModel build() throws Exception {
      if (this.shares.isEmpty()) {
        throw new Exception("Cannot create empty portfolio.");
      }
      return new FlexiblePortfolio(this);
    }
  }

  protected double getPortfolioValue(String date) throws Exception {
    if (this.isBeforeCreationDate(date)){
      return 0.0;
    }
    if (date.equals(this.creationDate)){
      return getCostAtDate(date);
    }
    return super.getPortfolioValue(date);
  }

  private boolean isBeforeCreationDate(String date) {
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate givenDate = LocalDate.parse(date, inputFormatter);
    LocalDate createdDate = LocalDate.parse(this.creationDate, inputFormatter);
    return givenDate.isBefore(createdDate);
  }

  private int getTotalQuantityOfSpecificShare(String tickerSymbol) {
    int quantity = 0;
    if (this.shares.containsKey(tickerSymbol)){
      for (ShareModel s : this.shares.get(tickerSymbol)){
        quantity = quantity + s.getQuantity();
      }
    }
    return quantity;
  }

  private boolean enoughSharesToSell(int desiredSellAmount, int actualAmount) {
    return actualAmount - desiredSellAmount >= 0;
  }

  private void removeSharesFromPortfolio(String share, int desiredSellQuantity) {
    int listIndexer = 0;
    while (desiredSellQuantity > 0) {
      if (this.shares.get(share).get(listIndexer).getQuantity() > desiredSellQuantity){
        int endQuantity = this.shares.get(share).get(listIndexer).
          getQuantity() - desiredSellQuantity;
        this.shares.get(share).get(listIndexer).setQuantity(endQuantity);
        desiredSellQuantity = 0;
      }

      else {
        desiredSellQuantity = desiredSellQuantity - this.shares.get(share).
          get(listIndexer).getQuantity();
        this.shares.get(share).remove(listIndexer);
        if (this.shares.get(share).isEmpty()) {
          this.shares.remove(share);
        }
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

  private void updateCostMap() {
    double todayCost = this.getCostBasisValue();
    this.costBasisMap.put(LocalDate.now().toString(), new Double(todayCost));
  }

  protected double getCostAtDate(String date) {
    Map.Entry<String, Double> entry = ((TreeMap) this.costBasisMap).floorEntry(date);
    return entry != null ? entry.getValue() : 0.0;
  }

}
