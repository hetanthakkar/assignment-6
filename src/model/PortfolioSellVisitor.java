package model;

class PortfolioSellVisitor implements PortfolioVisitorModel {

  private String tickerSymbol;
  private int quantity;

  /**
   * Constructs a new PortfolioSellVisitor with the specified ticker symbol
   * and quantity of shares to sell.
   *
   * @param tickerSymbol The ticker symbol of the shares to sell.
   * @param quantity     The quantity of shares to sell.
   */
  public PortfolioSellVisitor(String tickerSymbol, int quantity) {
    this.tickerSymbol = tickerSymbol;
    this.quantity = quantity;
  }

  /**
   * Visits a FlexiblePortfolioModel portfolio and sells shares.
   *
   * @param portfolio The FlexiblePortfolioModel portfolio to visit.
   * @throws Exception if an error occurs while selling shares.
   */
  @Override
  public void visit(FlexiblePortfolioModel portfolio) throws Exception {
    portfolio.sellShare(this.tickerSymbol, this.quantity);
  }

  /**
   * Visits a PortfolioModel portfolio.
   * This operation is not supported for inflexible portfolios.
   *
   * @param portfolio The PortfolioModel portfolio to visit.
   * @throws UnsupportedOperationException if an attempt is made to sell shares
   *                                         in an inflexible portfolio.
   */
  @Override
  public void visit(PortfolioModel portfolio) {
    throw new UnsupportedOperationException("Inflexible portfolio cannot sell shares");
  }
}

