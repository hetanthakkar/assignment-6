package model;

class PortfolioSellVisitor implements PortfolioVisitorModel {

  @Override
  public void visit(FlexiblePortfolioModel portfolio) throws Exception {
    portfolio.sellShare(this.tickerSymbol, this.quantity);
  }

  @Override
  public void visit(PortfolioModel portfolio) {
    throw new UnsupportedOperationException("Inflexible portfolio cannot sell shares");
  }
}
