package controller;

import java.util.ArrayList;
import java.util.List;

import model.AccountModel;
import model.ParsedShares;
import view.AccountView;

/**
 * The CreateTextCommandExecutor class is responsible
 * for executing the text command to create a portfolio.
 * It extends the AbstractTextCommandExecutor class
 * and implements the executeCommand() method to fulfill
 * the contract of the TextCommandExecutor interface.
 */
class CreateTextCommandExecutor extends AbstractTextCommandExecutor{

  /** The type of portfolio to be created. */
  private String portType;

  /** An array containing parts of the input command. */
  private String[] partOfInput;

  /**
   * Constructs a CreateTextCommandExecutor with the specified model, view,
   * portfolio type, and rest of the command.
   *
   * @param model         the model representing the account
   * @param view          the view representing the account
   * @param portType      the type of portfolio to be created
   * @param restOfCommand the remaining part of the command containing share information
   */
  CreateTextCommandExecutor(AccountModel model, AccountView view, String portType,
                            String restOfCommand){
    super(model, view);
    this.portType = portType;
    partOfInput = restOfCommand.split(" ");
  }

  /**
   * Executes the command to create a portfolio.
   * It parses the input command to extract share information, sets the portfolio name
   * and type using the model,
   * adds shares to the portfolio, and finishes building the portfolio.
   * It displays appropriate messages
   * using the view based on the outcome of the creation process.
   */
  @Override
  public void executeCommand() {
    if (partOfInput.length <= 1) {
      view.displayMessage("Cannot create empty portfolio.");
    }
    String portfolioName = partOfInput[0];
    try {
      List<ParsedShares> listNewShares = this.parseCreatePortfolioAndShare();
      model.setPortfolioName(portfolioName, portType);
      for (ParsedShares i : listNewShares) {
        model.addShare(i.getTickerSymbol(), i.getQuantity());
      }
      model.finishBuild();
      view.displayMessage(String.format("Successfully created %s", portfolioName));
    } catch (Exception e) {
      view.displayMessage(e.getMessage() + String.format(". %s was not created.", portfolioName));
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
    for (int i = 1; i < this.partOfInput.length; i++) {
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
