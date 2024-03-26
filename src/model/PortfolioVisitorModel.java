package model;

interface PortfolioVisitorModel {
  void visit(FlexiblePortfolioModel portfolio) throws Exception;
  void visit(PortfolioModel portfolio);
}
