package model;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class FlexiblePortfolio extends AbstractPortfolio implements FlexiblePortfolioModel{

  FlexiblePortfolio(PortfolioBuilder portfolioBuilder){
    super(portfolioBuilder);
  }
  @Override
  public void buyShare(String tickerSymbol, int quantity) throws Exception{
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
  }

  @Override
  public void sellShare(String share, int quantity) throws Exception{
    // first check if portfolio has share
    // // if no, throw exception ("this share does not exist in portfolio")
    if (!this.shares.containsKey(share)){
      throw new Exception("Cannot sell stock that is not in the portfolio.");
    }
    if (!this.enoughSharesToSell(quantity,this.getTotalQuantityOfSpecificShare(share))){
      throw new Exception(String.format("Not enough shares of %s to sell.", share));
    }
    else {
      this.removeSharesFromPortfolio(share, quantity);
    }
    // // // if yes, then start selling from most past date --> write a function for this logic.
  }


  //create helper methods to help with sell method
  // yes // private method to find total quantity of a share, to make sure there are enough to fulfill a sell order
  // yes // private method to check if portfolio has a specific share to make sure it can sell
  // // private method to get the Purchase Share of the most past date, so that it can start selling from there

  @Override
  public void accept(PortfolioVisitorModel visitor) throws Exception{
    visitor.visit(this);
  }

  public static class FlexiblePortfolioBuilder extends PortfolioBuilder{

    @Override
    PortfolioModel build() throws Exception {
      if (this.shares.isEmpty()) {
        throw new Exception("Cannot create empty portfolio.");
      }
      return new FlexiblePortfolio(this);
    }
  }

  protected double getPortfolioValue(String date) throws Exception{
    if (this.isBeforeCreationDate(date)){
      return 0.0;
    }
    return super.getPortfolioValue(date);
  }

  private boolean isBeforeCreationDate(String date){
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate givenDate = LocalDate.parse(date, inputFormatter);
    LocalDate createdDate = LocalDate.parse(this.creationDate, inputFormatter);
    return givenDate.isBefore(createdDate);
  }

  private int getTotalQuantityOfSpecificShare(String tickerSymbol){
    int quantity = 0;
    if (this.shares.containsKey(tickerSymbol)){
      for (ShareModel s : this.shares.get(tickerSymbol)){
        quantity = quantity + s.getQuantity();
      }
    }
    return quantity;
  }

  private boolean enoughSharesToSell (int desiredSellAmount, int actualAmount){
    return actualAmount - desiredSellAmount >= 0;
  }

  private void removeSharesFromPortfolio(String share, int desiredSellQuantity) {
    int listIndexer = 0;
    while (desiredSellQuantity > 0){
      if (this.shares.get(share).get(listIndexer).getQuantity() > desiredSellQuantity){
        int endQuantity = this.shares.get(share).get(listIndexer).getQuantity() - desiredSellQuantity;
        this.shares.get(share).get(listIndexer).setQuantity(endQuantity);
        desiredSellQuantity = 0;
      }
      else {
        desiredSellQuantity = desiredSellQuantity - this.shares.get(share).get(listIndexer).getQuantity();
        this.shares.get(share).remove(listIndexer);
      }
    }
  }

}
