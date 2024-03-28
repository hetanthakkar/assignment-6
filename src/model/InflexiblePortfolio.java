package model;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Portfolio class represents a financial portfolio containing shares of
 * various ticker symbols.
 * It provides methods for managing the composition of the portfolio, including
 * adding and removing shares,
 * as well as calculating the total value of the portfolio based on current
 * market prices.
 */

class InflexiblePortfolio extends AbstractPortfolio implements PortfolioModel {
  String name;
  Map<String, List<ShareModel>> shares;

  String creationDate;

  InflexiblePortfolio(PortfolioBuilder newBuild){
    super(newBuild);
  }


  @Override
  public void accept(PortfolioVisitorModel visitor) throws Exception {
    visitor.visit(this);
  }

  /**
   * The PortfolioBuilder class provides a fluent interface for building Portfolio
   * objects.
   */
  public static class InflexiblePortfolioBuilder extends PortfolioBuilder {

    PortfolioModel build() throws Exception {
      if (this.shares.isEmpty()) {
        throw new Exception("Cannot create empty portfolio.");
      }
      return new InflexiblePortfolio(this);
    }
  }
}
