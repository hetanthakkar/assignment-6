import model.AccountModel;

/**
 * The MockModel class is an implementation of the AccountModel interface.
 * It is likely used for testing or mocking purposes in the application.
 * This class provides a simple implementation of the AccountModel methods,
 * setting various flags to indicate when certain methods are called.
 */
public class MockModel implements AccountModel {

  public boolean setPortfolioNameFlag;
  public boolean addShareFlag;
  public boolean finishBuildFlag;

  /**
   * Constructs a new MockModel instance.
   * Initializes the flags to false.
   */
  public MockModel() {
    this.setPortfolioNameFlag = false;
    this.addShareFlag = false;
    this.finishBuildFlag = false;
  }

  /**
   * Sets the portfolio name and type.
   * Sets the setPortfolioNameFlag to true.
   *
   * @param name          The name of the portfolio.
   * @param portfolioType The type of the portfolio.
   */
  @Override
  public void setPortfolioName(String name, String portfolioType) {
    this.setPortfolioNameFlag = true;
  }

  /**
   * Adds a share to the portfolio.
   * Sets the addShareFlag to true.
   *
   * @param tickerSymbol The ticker symbol of the share.
   * @param quantity     The quantity of the share.
   * @throws Exception If an error occurs while adding the share.
   */
  @Override
  public void addShare(String tickerSymbol, int quantity) throws Exception {
    this.addShareFlag = true;
  }

  /**
   * Finishes building the portfolio.
   * Sets the finishBuildFlag to true.
   *
   * @throws Exception If an error occurs while finishing the build.
   */
  @Override
  public void finishBuild() throws Exception {
    this.finishBuildFlag = true;
  }

  /**
   * Returns the composition of the portfolio with the given name.
   *
   * @param portfolioName The name of the portfolio.
   * @return The composition of the portfolio.
   */
  @Override
  public String getPortfolioComposition(String portfolioName) {
    return "Portfolio Composition.";
  }

  /**
   * Returns the total value of the portfolio with the given name at a certain date.
   *
   * @param portfolioName The name of the portfolio.
   * @param date          The date for which the total value is needed.
   * @return The total value of the portfolio at the given date.
   * @throws Exception If an error occurs while retrieving the total value.
   */
  @Override
  public String getPortfolioTotalValueAtCertainDate(String portfolioName, String date)
          throws Exception {
    return "Portfolio Total Value";
  }

  /**
   * Returns a list of portfolios.
   *
   * @return A list of portfolios.
   */
  @Override
  public String listPortfolios() {
    return "List of Portfolio";
  }

  /**
   * Saves the portfolio with the given name.
   *
   * @param portfolioName The name of the portfolio to save.
   * @return A message indicating that the portfolio is being saved.
   * @throws Exception If an error occurs while saving the portfolio.
   */
  @Override
  public String savePortfolio(String portfolioName) throws Exception {
    return "Saving Portfolio";
  }

  /**
   * Buys a share for the portfolio with the given name.
   *
   * @param portfolioName The name of the portfolio.
   * @param tickerSymbol  The ticker symbol of the share.
   * @param quantity      The quantity of the share to buy.
   * @throws Exception If an error occurs while buying the share.
   */
  @Override
  public void buyShare(String portfolioName, String tickerSymbol, int quantity) throws Exception {
    // No implementation provided
  }

  /**
   * Sells a share from the portfolio with the given name.
   *
   * @param portfolioName The name of the portfolio.
   * @param tickerSymbol  The ticker symbol of the share.
   * @param quantity      The quantity of the share to sell.
   * @throws Exception If an error occurs while selling the share.
   */
  @Override
  public void sellShare(String portfolioName, String tickerSymbol, int quantity) throws Exception {
    // No implementation provided
  }

  /**
   * Returns the cost basis of the portfolio with the given name at a certain date.
   *
   * @param portfolioName The name of the portfolio.
   * @param date          The date for which the cost basis is needed.
   * @return The cost basis of the portfolio at the given date.
   * @throws Exception If an error occurs while retrieving the cost basis.
   */
  @Override
  public String getCostBasis(String portfolioName, String date) throws Exception {
    return null;
  }

  /**
   * Loads the portfolio with the given name.
   *
   * @param portfolioName The name of the portfolio to load.
   * @return A message indicating that the portfolio is being loaded.
   * @throws Exception If an error occurs while loading the portfolio.
   */
  @Override
  public String loadModel(String portfolioName) throws Exception {
    return null;
  }
}