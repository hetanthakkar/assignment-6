package model;

/**
 * The FlexiblePortfolioModel interface represents a financial portfolio that allows
 * buying and selling shares dynamically.
 * It extends the PortfolioModel interface, inheriting methods for managing the portfolio's
 * composition and calculating its total value, and adds methods for buying and selling shares.
 */
public interface FlexiblePortfolioModel extends PortfolioModel {

  /**
   * Buys shares and adds them to the portfolio.
   *
   * @param share    The ticker symbol of the shares to buy.
   * @param quantity The quantity of shares to buy.
   * @throws Exception if an error occurs while buying shares.
   */

  void buyShare(String share, int quantity) throws Exception;

  /**
   * Sells shares from the portfolio.
   *
   * @param share    The ticker symbol of the shares to sell.
   * @param quantity The quantity of shares to sell.
   * @throws Exception if an error occurs while selling shares.
   */

  void sellShare(String share, int quantity) throws Exception;

}

