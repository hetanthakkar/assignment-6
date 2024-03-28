package model;

import java.util.HashMap;
import java.util.Map;
import model.FlexiblePortfolio.FlexiblePortfolioBuilder;
import model.InflexiblePortfolio.InflexiblePortfolioBuilder;

/**
 * The Account class represents a user account. It contains portfolios and
 * allows operations
 * such as adding shares, retrieving portfolio composition, and saving
 * portfolios.
 */
public class Account implements AccountModel {

  private Map<String, PortfolioModel> accountPortfolios;
  private AccountBuilder accountBuild;

  /**
   * Constructs a new Account object.
   */
  public Account() {
    accountPortfolios = new HashMap<>();
    accountBuild = new AccountBuilder();
  }

  @Override
  public void setPortfolioName(String name, String portfolioType) {
    this.accountBuild.createPortfolio(name, portfolioType);
  }

  @Override
  public void addShare(String tickerSymbol, int quantity) throws Exception {
    this.accountBuild.addShare(tickerSymbol, quantity);
  }

  @Override
  public void finishBuild() throws Exception {
    this.accountPortfolios = this.accountBuild.build();
    this.accountBuild = new AccountBuilder();
  }

  @Override
  public String getPortfolioComposition(String portfolioName) {
    if (this.accountPortfolios.containsKey(portfolioName)) {
      return this.accountPortfolios.get(portfolioName).getPortfolioComposition();
    } else {
      throw new IllegalArgumentException("Portfolio does not exist.");
    }
  }

  @Override
  public String getPortfolioTotalValueAtCertainDate(String portfolioName, String date)
          throws Exception {
    if (this.accountPortfolios.containsKey(portfolioName)) {
      try {
        return this.accountPortfolios.get(portfolioName).getTotalValueAtCertainDate(date);
      } catch (Exception e) {
        throw e;
      }
    } else {
      throw new IllegalArgumentException("Portfolio does not exist.");
    }
  }

  @Override
  public String listPortfolios() {
    StringBuilder listOfPortfolios = new StringBuilder("List of Portfolios:\n");
    for (Map.Entry<String, PortfolioModel> entry : accountPortfolios.entrySet()) {
      listOfPortfolios.append("|---");
      listOfPortfolios.append(entry.getKey());
      listOfPortfolios.append("\n");
    }
    return listOfPortfolios.toString();
  }

  @Override
  public String savePortfolio(String portfolioName) throws Exception {
    if (this.accountPortfolios.containsKey(portfolioName)) {
      try {
        return this.accountPortfolios.get(portfolioName).savePortfolio();
      } catch (Exception e) {
        throw e;
      }
    } else {
      throw new IllegalArgumentException("Portfolio does not exist.");
    }
  }

  @Override
  public void buyShare(String portfolioName, String tickerSymbol, int quantity) throws Exception{
    if (!this.accountPortfolios.containsKey(portfolioName)){
      throw new IllegalArgumentException("Portfolio does not exist.");
    }
    try {
      this.accountPortfolios.get(portfolioName).accept(new PortfolioBuyVisitor(tickerSymbol,quantity));
    }
    catch (Exception e){
      throw e;
    }
  }

  @Override
  public void sellShare(String portfolioName, String tickerSymbol, int quantity) throws Exception{
    if (!this.accountPortfolios.containsKey(portfolioName)){
      throw new IllegalArgumentException("Portfolio does not exist.");
    }
    try {
      this.accountPortfolios.get(portfolioName).accept(new PortfolioSellVisitor(tickerSymbol,quantity));
    }
    catch (Exception e){
      throw e;
    }
  }

  /**
   * The AccountBuilder class provides methods for building portfolios within an
   * account.
   */
  static class AccountBuilder {
    private static final Account account = new Account();
    private String portfolioName;
    private PortfolioBuilder portfolioBuild;

    /**
     * Creates a new portfolio with the given name.
     *
     * @param portfolioName The name of the portfolio to create.
     * @return The AccountBuilder object.
     * @throws IllegalArgumentException if the portfolio already exists.
     */
    AccountBuilder createPortfolio(String portfolioName, String portfolioType) {
      // portfolio builder now abstract, define specific builder by taking in a new field specifying type
      if (account.accountPortfolios.containsKey(portfolioName)) {
        throw new IllegalArgumentException(String.format("%s is a portfolio that already" +
                " exists.", portfolioName));
      }
      this.portfolioName = portfolioName;
      if (portfolioType == "flexible"){
        this.portfolioBuild = new FlexiblePortfolioBuilder().createPortfolio(portfolioName);
      }
      else {
        this.portfolioBuild = new InflexiblePortfolioBuilder().createPortfolio(portfolioName);
      }
      return this;
    }

    /**
     * Adds shares to the currently building portfolio.
     *
     * @param tickerSymbol The ticker symbol of the shares to add.
     * @param quantity     The quantity of shares to add.
     * @return The AccountBuilder object.
     * @throws Exception if there is an error while adding shares.
     */
    AccountBuilder addShare(String tickerSymbol, int quantity) throws Exception {
      portfolioBuild.addShares(tickerSymbol, quantity);
      return this;
    }

    /**
     * Builds the portfolios within the account.
     *
     * @return A map of portfolio names to their respective PortfolioModels.
     * @throws Exception if there is an error while building portfolios.
     */
    Map<String, PortfolioModel> build() throws Exception {
      account.accountPortfolios.put(portfolioName, portfolioBuild.build());
      return account.accountPortfolios;
    }
  }
}
