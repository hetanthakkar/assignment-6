package model;


/**
 * The PurchaseShares class represents shares purchased by a user. It includes
 * information
 * such as the ticker symbol of the purchased shares, the quantity purchased,
 * and the
 * date of the purchase, providing a record of user transactions for stock
 * purchases.
 */

class PurchaseShares extends Share {

  private int quantity;


  /**
   * Constructs a new PurchaseShares object with the specified ticker symbol and
   * quantity.
   *
   * @param tickerSymbol The ticker symbol of the shares.
   * @param quantity     The quantity of shares purchased.
   * @throws Exception if an error occurs while constructing the PurchaseShares
   *                   object.
   */
  PurchaseShares(String tickerSymbol, int quantity) throws Exception {
    super(tickerSymbol);
    this.quantity = quantity;
    this.cost = this.getCurrentValue();
  }

  @Override
  public double getCost() {
    return this.cost;
  }

  @Override
  public double getCurrentValue() throws Exception {
    return super.getCurrentValue() * this.quantity;
  }

  @Override
  public int getQuantity() {
    return this.quantity;
  }

  @Override
  public double getValueAtDate(String date) throws Exception {
    return super.getValueAtDate(date) * this.quantity;
  }

  @Override
  public void setQuantity(int newQuantity)
  {
    updateCostWhenSettingQuanitity(newQuantity);
    this.quantity = newQuantity;
  }

  private void updateCostWhenSettingQuanitity(int newQuantity)
  {
    this.cost = this.cost / this.quantity * newQuantity;
  }
}
