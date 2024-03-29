package model;


/**
 * The InflexiblePortfolio class represents a financial portfolio with a fixed
 * composition of shares.
 * It extends the AbstractPortfolio class and implements the PortfolioModel interface,
 * providing methods for managing the composition of the portfolio and calculating its total value.
 */
class InflexiblePortfolio extends AbstractPortfolio implements PortfolioModel {

  /**
   * Constructs a new InflexiblePortfolio with the specified builder.
   *
   * @param newBuild The PortfolioBuilder used to build the portfolio.
   */
  InflexiblePortfolio(PortfolioBuilder newBuild) {
    super(newBuild);
  }

  /**
   * Accepts a visitor for the InflexiblePortfolio.
   *
   * @param visitor The PortfolioVisitorModel visitor to accept.
   * @throws Exception if an error occurs while accepting the visitor.
   */
  @Override
  public void accept(PortfolioVisitorModel visitor) throws Exception {
    visitor.visit(this);
  }

  /**
   * The InflexiblePortfolioBuilder class provides a fluent interface for building
   * InflexiblePortfolio objects.
   */
  public static class InflexiblePortfolioBuilder extends PortfolioBuilder {

    /**
     * Builds the InflexiblePortfolio.
     *
     * @return The constructed InflexiblePortfolio.
     * @throws Exception if the portfolio is empty.
     */
    @Override
    PortfolioModel build() throws Exception {
      if (this.shares.isEmpty()) {
        throw new Exception("Cannot create empty portfolio.");
      }
      return new InflexiblePortfolio(this);
    }
  }
}

