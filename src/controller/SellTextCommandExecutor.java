package controller;

import java.util.ArrayList;
import java.util.List;

import model.AccountModel;
import model.ParsedShares;
import view.AccountView;

/**
 * The SellTextCommandExecutor class is responsible
 * for executing the text command to sell shares from a portfolio.
 * It extends the AbstractTextCommandExecutor class and implements
 * the executeCommand() method to fulfill the contract of the TextCommandExecutor interface.
 */
class SellTextCommandExecutor extends AbstractTextCommandExecutor {

  /** An array containing parts of the input command. */
  private String[] partOfInput;

  /**
   * Constructs a SellTextCommandExecutor with the specified model, view, and rest of the command.
   *
   * @param model         the model representing the account
   * @param view          the view representing the account
   * @param restOfCommand the remaining part of the command containing share information
   */
  SellTextCommandExecutor(AccountModel model, AccountView view, String restOfCommand) {
    super(model, view);
    this.partOfInput = restOfCommand.split(" ");
  }

  /**
   * Executes the command to sell shares from a portfolio.
   * It parses the input command to extract share information,
   * then sells the shares using the model.
   * It displays appropriate messages using the view based on the outcome of the selling process.
   */
  @Override
  public void executeCommand() {
    if (partOfInput.length <= 1) {
      view.displayMessage("Cannot sell nothing.");
    }
    String portfolioName = partOfInput[0];
    String date = partOfInput[1];
    try {
      List<ParsedShares> listNewShares = this.parseCreatePortfolioAndShare();
      for (ParsedShares i : listNewShares) {
        model.sellShare(portfolioName, i.getTickerSymbol(), i.getQuantity(), date);
        view.displayMessage(String.format("Successfully sold %d-%s in %s", i.getQuantity(),
                i.getTickerSymbol(), portfolioName));
      }
    } catch (Exception e) {
      view.displayMessage(e.getMessage() + " Selling was not done.");
    }
  }

  /**
   * Parses the input command to extract share information.
   *
   * @return a list of ParsedShares objects containing share information
   * @throws IllegalArgumentException if the input command is not formatted correctly
   */
  protected List<ParsedShares> parseCreatePortfolioAndShare() {
    List<ParsedShares> shares = new ArrayList<ParsedShares>();
    for (int i = 2; i < this.partOfInput.length; i++) {
      String shareInput = partOfInput[i];
      if (shareInput.startsWith("(") && shareInput.endsWith(")")) {
        String[] shareInfo = shareInput.substring(1, shareInput.length() - 1).split("-");
        int quantity;
        try {
          quantity = Integer.parseInt(shareInfo[1]);
        } catch (Exception e) {
          throw new IllegalArgumentException(
                  "Quantity must be non-fractional "
                          + "and after Ticker Symbol. i.e: ([TickerSymbol]-[quantity])");
        }
        ParsedShares newShare = new ParsedShares(shareInfo[0], quantity);
        shares.add(newShare);
      } else {
        throw new IllegalArgumentException(
                "Please input shares correctly (no spaces). i.e: ([TickerSymbol]-[quantity])");
      }
    }
    return shares;
  }
}
