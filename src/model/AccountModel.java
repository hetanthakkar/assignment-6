package model;

import java.util.Map;

/**
 * An interface that defines the behavior of an account that holds multiple portfolios.
 * It provides methods for managing portfolios, including adding shares,
 * retrieving composition,
 * and saving portfolios to a file. Implementing classes should provide concrete
 * implementations for these methods.
 */
public interface AccountModel {

  /**
   * Sets a name for a portfolio object.
   *
   * @param name          Name of portfolio
   * @param portfolioType Type of portfolio
   */
  void setPortfolioName(String name, String portfolioType);

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

  /**
   * Buys shares for a portfolio.
   *
   * @param portfolioName Name of portfolio
   * @param tickerSymbol  Ticker symbol of stock
   * @param quantity      Quantity of shares to buy
   * @throws Exception when buying shares is not possible
   */
  void buyShare(String portfolioName, String tickerSymbol, double quantity, String date) throws Exception;

  /**
   * Sells shares from a portfolio.
   *
   * @param portfolioName Name of portfolio
   * @param tickerSymbol  Ticker symbol of stock
   * @param quantity      Quantity of shares to sell
   * @throws Exception when selling shares is not possible
   */
  void sellShare(String portfolioName, String tickerSymbol, double quantity, String date) throws Exception;

  /**
   * Returns the cost basis of a portfolio on a given date.
   *
   * @param portfolioName Name of portfolio
   * @param date          Desired date
   * @return The cost basis of the portfolio on the given date
   * @throws Exception when invalid portfolio or date is given
   */
  String getCostBasis(String portfolioName, String date) throws Exception;

  String loadModel(String portfolioName) throws Exception;

  void buyStrategy(String portName, double investAmount, Map<String,Double> sharePercentage) throws Exception;

}
