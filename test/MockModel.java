import model.AccountModel;

public class MockModel implements AccountModel {

  public boolean setPortfolioNameFlag;
  public boolean addShareFlag;

  public boolean finishBuildFlag;

  public MockModel() {
    this.setPortfolioNameFlag = false;
    this.addShareFlag = false;
    this.finishBuildFlag = false;
  }

  @Override
  public void setPortfolioName(String name, String portfolioType) {
    this.setPortfolioNameFlag = true;
  }

  @Override
  public void addShare(String tickerSymbol, int quantity) throws Exception {
    this.addShareFlag = true;
  }

  @Override
  public void finishBuild() throws Exception {
    this.finishBuildFlag = true;
  }

  @Override
  public String getPortfolioComposition(String portfolioName) {
    return "Portfolio Composition.";
  }

  @Override
  public String getPortfolioTotalValueAtCertainDate(String portfolioName, String date)
          throws Exception {
    return "Portfolio Total Value";
  }

  @Override
  public String listPortfolios() {
    return "List of Portfolio";
  }

  @Override
  public String savePortfolio(String portfolioName) throws Exception {
    return "Saving Portfolio";
  }

  @Override
  public void buyShare(String portfolioName, String tickerSymbol, int quantity) throws Exception {

  }

  @Override
  public void sellShare(String portfolioName, String tickerSymbol, int quantity) throws Exception {

  }
}
