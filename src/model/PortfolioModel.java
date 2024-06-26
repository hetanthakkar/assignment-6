package model;

/**
 * The PortfolioModel interface represents a financial portfolio. It provides
 * methods to retrieve
 * the total value of the portfolio at a certain date, its composition (list of
 * shares and quantities),
 * and to save the portfolio for future reference or analysis.
 */

public interface PortfolioModel {

  /**
   * Retrieves the total value of the portfolio at a given date.
   *
   * @param date The date for which to retrieve the total value of the portfolio.
   * @return A string representation of the total value of the portfolio on the
   *     specified date.
   * @throws Exception if there is an error while retrieving the total value.
   */
  String getTotalValueAtCertainDate(String date) throws Exception;

  /**
   * Retrieves the composition of the portfolio.
   *
   * @return A string representation of the composition of the portfolio.
   */
  String getPortfolioComposition();

  /**
   * Saves the portfolio.
   *
   * @return A string indicating the success of the operation.
   * @throws Exception if there is an error while saving the portfolio.
   */
  String savePortfolio() throws Exception;

  /**
   * Retrieves the cost basis of the portfolio on a given date.
   *
   * @param date The date for which to retrieve the cost basis of the portfolio.
   * @return A string representation of the cost basis of the portfolio on the
   *     specified date.
   */
  String getCostBasis(String date);

  /**
   * Accepts a visitor to perform operations on the portfolio.
   *
   * @param visitor The visitor to accept.
   * @throws Exception if there is an error while accepting the visitor.
   */
  void accept(PortfolioVisitorModel visitor) throws Exception;

  String generatePerformanceBarChart(String startDate, String endDate) throws Exception;
}

