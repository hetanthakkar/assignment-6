package model;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import model.FlexiblePortfolio.FlexiblePortfolioBuilder;
import model.InflexiblePortfolio.InflexiblePortfolioBuilder;

/**
 * The Account class represents a user account. It contains portfolios and allows operations such as
 * adding shares, retrieving portfolio composition, and saving portfolios.
 */
public class Account implements AccountModel {

  private Map<String, PortfolioModel> accountPortfolios;
  private AccountBuilder accountBuild;

  /** Constructs a new Account object. */
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
  public void buyShare(String portfolioName, String tickerSymbol, double quantity, String date) throws Exception {
    if (!this.accountPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("Portfolio does not exist.");
    }
    try {
      this.accountPortfolios
          .get(portfolioName)
          .accept(new PortfolioBuyVisitor(tickerSymbol, quantity, date));
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public void sellShare(String portfolioName, String tickerSymbol, double quantity, String date) throws Exception {
    if (!this.accountPortfolios.containsKey(portfolioName)) {
      throw new IllegalArgumentException("Portfolio does not exist.");
    }
    try {
      this.accountPortfolios
          .get(portfolioName)
          .accept(new PortfolioSellVisitor(tickerSymbol, quantity, date));
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public String getCostBasis(String portfolioName, String date) throws Exception {
    if (this.accountPortfolios.containsKey(portfolioName)) {
      try {
        return this.accountPortfolios.get(portfolioName).getCostBasis(date);
      } catch (Exception e) {
        throw e;
      }
    } else {
      throw new IllegalArgumentException("Portfolio does not exist.");
    }
  }

  @Override
  public String loadModel( String restOfCommand) throws Exception {
    String[] partOfInput = restOfCommand.split(" ");
    int lastIndex = partOfInput[0].lastIndexOf("/");
    String fileLocation = partOfInput[0].substring(0, lastIndex);
    String fileName = partOfInput[0].substring(lastIndex + 1);
    CsvFileIOInterface csvFileIO = new CsvFileIO(fileName, fileLocation);
    CsvProcessorInterface csvProcessor = new CsvProcessor(csvFileIO.getData());
    String portfolioName1 = csvProcessor.getPortfolioNameFromCsv();
    System.out.println(portfolioName1);
    this.setPortfolioName(portfolioName1, "flexible");
    try {
      List<ParsedShares> listNewShares = csvProcessor.getSharesFromCsv();
      for (model.ParsedShares i : listNewShares) {
        this.addShare(i.getTickerSymbol(), i.getQuantity());
        System.out.println(i.getTickerSymbol() + "As" + i.getQuantity());
      }
      this.finishBuild();
    } catch (Exception e) {
      return (e.getMessage()
        + String.format(". %s was not  created.", portfolioName1));

    }
    return (String.format("Succesfully created %s", portfolioName1));
  }

  @Override
  public void buyStrategy(String portName, double investAmount, Map<String, Double> sharePercentage) throws Exception {
    if (this.accountPortfolios.containsKey(portName)){
      for (Map.Entry<String,Double> entry : sharePercentage.entrySet()){
        ShareModel share;
        try {
          share = new Share(entry.getKey());
          double numOfShare = (investAmount * entry.getValue() / 100.0) / share.getCurrentValue();
          this.buyShare(portName, entry.getKey(), numOfShare, LocalDate.now().toString());
        }
        catch (Exception e){
          throw e;
        }

      }
    }
  }

  /** The AccountBuilder class provides methods for building portfolios within an account. */
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
      // portfolio builder now abstract, define specific builder by taking in a new field specifying
      // type
      if (account.accountPortfolios.containsKey(portfolioName)) {
        throw new IllegalArgumentException(
            String.format("%s is a portfolio that already" + " exists.", portfolioName));
      }
      this.portfolioName = portfolioName;
      if (Objects.equals(portfolioType, "flexible")) {
        this.portfolioBuild = new FlexiblePortfolioBuilder().createPortfolio(portfolioName);
      } else {
        this.portfolioBuild = new InflexiblePortfolioBuilder().createPortfolio(portfolioName);
      }
      return this;
    }

    /**
     * Adds shares to the currently building portfolio.
     *
     * @param tickerSymbol The ticker symbol of the shares to add.
     * @param quantity The quantity of shares to add.
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
