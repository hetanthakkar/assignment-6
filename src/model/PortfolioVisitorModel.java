package model;

/**
 * The PortfolioVisitorModel interface defines the behavior of a visitor
 * that can visit different types of portfolios.
 * Implementing classes should provide concrete implementations for
 * the visit methods to perform specific actions on the portfolios.
 */
public interface PortfolioVisitorModel {

  /**
   * Visits a FlexiblePortfolioModel portfolio.
   *
   * @param portfolio The FlexiblePortfolioModel portfolio to visit.
   * @throws Exception if an error occurs while visiting the portfolio.
   */
  void visit(FlexiblePortfolioModel portfolio) throws Exception;

  /**
   * Visits a PortfolioModel portfolio.
   *
   * @param portfolio The PortfolioModel portfolio to visit.
   */
  void visit(PortfolioModel portfolio);
}

