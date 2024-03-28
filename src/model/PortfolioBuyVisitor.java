package model;

/**
 * The PortfolioBuyVisitor class implements the PortfolioVisitorModel interface
 * and represents a visitor that buys shares in portfolios.
 * It provides methods to visit different types of portfolios and perform
 * the buying operation.
 */
public class PortfolioBuyVisitor implements PortfolioVisitorModel {

  private String tickerSymbol;
  private int quantity;

  /**
   * Constructs a new PortfolioBuyVisitor with the specified ticker symbol
   * and quantity of shares to buy.
   *
   * @param tickerSymbol The ticker symbol of the shares to buy.
   * @param quantity     The quantity of shares to buy.
   */
  public PortfolioBuyVisitor(String tickerSymbol, int quantity) {
    this.tickerSymbol = tickerSymbol;
    this.quantity = quantity;
  }

  /**
   * Visits a FlexiblePortfolioModel portfolio and buys shares.
   *
   * @param portfolio The FlexiblePortfolioModel portfolio to visit.
   * @throws Exception if an error occurs while buying shares.
   */
  @Override
  public void visit(FlexiblePortfolioModel portfolio) throws Exception {
    portfolio.buyShare(this.tickerSymbol, this.quantity);
  }

  /**
   * Visits a PortfolioModel portfolio.
   * This operation is not supported for inflexible portfolios.
   *
   * @param portfolio The PortfolioModel portfolio to visit.
   * @throws UnsupportedOperationException if an attempt is made to buy shares
   *                                         in an inflexible portfolio.
   */
  @Override
  public void visit(PortfolioModel portfolio) {
    throw new UnsupportedOperationException("Inflexible portfolio cannot buy shares");
  }
}
