package model;

class PortfolioBuyVisitor implements PortfolioVisitorModel {

  private String tickerSymbol;
  private int quantity;

  PortfolioBuyVisitor(String tickerSymbol, int quantity) {
    this.tickerSymbol = tickerSymbol;
    this.quantity = quantity;
  }

  @Override
  public void visit(FlexiblePortfolioModel portfolio) throws Exception {
    portfolio.buyShare(this.tickerSymbol, this.quantity);
  }

  @Override
  public void visit(PortfolioModel portfolio) {
    throw new UnsupportedOperationException("Inflexible portfolio cannot buy shares");
  }

}
