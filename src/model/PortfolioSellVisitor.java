package model;

class PortfolioSellVisitor implements PortfolioVisitorModel {

  private String tickerSymbol;
  private int quantity;

  PortfolioSellVisitor(String tickerSymbol, int quantity) {
    this.tickerSymbol = tickerSymbol;
    this.quantity = quantity;
  }

  @Override
  public void visit(FlexiblePortfolioModel portfolio) throws Exception {
    portfolio.sellShare(this.tickerSymbol, this.quantity);
  }

  @Override
  public void visit(PortfolioModel portfolio) {
    throw new UnsupportedOperationException("Inflexible portfolio cannot sell shares");
  }
}
