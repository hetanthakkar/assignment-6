package view;

import java.util.Date;

public class StockHolding {
  private String symbol;
  private double purchasedPrice;
  private double currentPrice;
  private int quantity;
  private Date purchasedDate;

  public StockHolding(
      String symbol, double purchasedPrice, double currentPrice, int quantity, Date purchasedDate) {
    this.symbol = symbol;
    this.purchasedPrice = purchasedPrice;
    this.currentPrice = currentPrice;
    this.quantity = quantity;
    this.purchasedDate = purchasedDate;
  }

  public String getSymbol() {
    return symbol;
  }

  public double getPurchasedPrice() {
    return purchasedPrice;
  }

  public double getCurrentPrice() {
    return currentPrice;
  }

  public int getQuantity() {
    return quantity;
  }

  public Date getPurchasedDate() {
    return purchasedDate;
  }

  public String getName() {
    return this.symbol;
  }

  public StockHolding[] getStockHoldings() {
    return null;
  }
}
