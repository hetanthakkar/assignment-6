package model;

/**
 * An interface that defines the behavior of an account that holds multiple
 * portfolios.
 * It provides methods for managing portfolios, including adding shares,
 * retrieving composition,
 * and saving portfolios to a file. Implementing classes should provide concrete
 * implementations for these methods.
 */
public interface AccountModel {

  /**
   * Sets a name for a portfolio object.
   *
   * @param name Name of portfolio
   */
  void setPortfolioName(String name);

  /**
   * Adds share to portfolio object.
   *
   * @param tickerSymbol Ticker symbol of stock
   * @param quantity     Quantity of shares
   * @throws Exception When adding share is not valid
   */
  void addShare(String tickerSymbol, int quantity) throws Exception;

  /**
   * Finish building a portfolio after setting name and adding shares.
   *
   * @throws Exception When portfolio is not valid to be created
   */
  void finishBuild() throws Exception;

  /**
   * Returns composition of desired portfolio.
   *
   * @param portfolioName Name of portfolio
   * @return Portfolio and its shares and quantities
   */
  String getPortfolioComposition(String portfolioName);

  /**
   * Returns the total value of a portfolio on a given date.
   *
   * @param portfolioName Name of portfolio
   * @param date          Desired date
   * @return Total value of portfolio on a given date
   * @throws Exception when invalid portfolio or date is given
   */
  String getPortfolioTotalValueAtCertainDate(String portfolioName, String date) throws Exception;

  /**
   * Returns list of existing portfolios.
   *
   * @return list of existing portfolios
   */
  String listPortfolios();

  /**
   * Saves given portfolio into a file.
   *
   * @param portfolioName Name of portfolio
   * @return Message on whether portfolio was saved
   * @throws Exception when given portfolio is invalid
   */
  String savePortfolio(String portfolioName) throws Exception;
}
