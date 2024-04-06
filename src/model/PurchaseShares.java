package model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * The PurchaseShares class represents shares purchased by a user. It includes
 * information
 * such as the ticker symbol of the purchased shares, the quantity purchased,
 * and the
 * date of the purchase, providing a record of user transactions for stock
 * purchases.
 */

class PurchaseShares extends Share {

  private double quantity;


  /**
   * Constructs a new PurchaseShares object with the specified ticker symbol and
   * quantity.
   *
   * @param tickerSymbol The ticker symbol of the shares.
   * @param quantity     The quantity of shares purchased.
   * @throws Exception if an error occurs while constructing the PurchaseShares
   *                   object.
   */
  PurchaseShares(String tickerSymbol, double quantity) throws Exception {
    super(tickerSymbol);
    this.quantity = quantity;
    this.cost = this.getCurrentValue();
  }

  PurchaseShares(String tickerSymbol, double quantity, String date) throws Exception {
    super(tickerSymbol);
    this.date = date;
    this.quantity = quantity;
    this.cost = this.getValueAtDate(date) * quantity;
  }



  @Override
  public double getCost() {
    return this.cost;
  }

  @Override
  public double getCostAtDate(String date){
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDate givenDate = LocalDate.parse(date, inputFormatter);
    LocalDate createdDate = LocalDate.parse(this.date, inputFormatter);
    if(givenDate.isBefore(createdDate)){
      return 0.0;
    }
    return this.cost;
  }

  @Override
  public double getCurrentValue() throws Exception {
    return super.getCurrentValue() * this.quantity;
  }

  @Override
  public double getQuantity() {
    return this.quantity;
  }

  @Override
  public double getValueAtDate(String date) throws Exception {
    return super.getValueAtDate(date) * this.quantity;
  }

  @Override
  public void setQuantity(double newQuantity) {
    updateCostWhenSettingQuanitity(newQuantity);
    this.quantity = newQuantity;
  }

  private void updateCostWhenSettingQuanitity(double newQuantity) {
    this.cost = this.cost / this.quantity * newQuantity;
  }
}
