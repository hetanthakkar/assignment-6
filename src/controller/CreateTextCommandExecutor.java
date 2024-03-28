package controller;

import java.util.ArrayList;
import java.util.List;

import model.AccountModel;
import model.ParsedShares;
import view.AccountView;

class CreateTextCommandExecutor extends AbstractTextCommandExecutor {

  String portType;
  String[] partOfInput;

  CreateTextCommandExecutor(AccountModel model, AccountView view, String portType,
    String restOfCommand) {
    super(model, view);
    this.portType = portType;
    partOfInput = restOfCommand.split(" ");
  }
  @Override

  public void executeCommand() {

    if (partOfInput.length <= 1) {
      view.displayMessage("Cannot create empty portfolio.");
    }
    String portfolioName = partOfInput[0];
    try {
      List<ParsedShares> listNewShares = this.parseCreatePortfolioAndShare();
      model.setPortfolioName(portfolioName,portType);
      for (ParsedShares i : listNewShares) {
        model.addShare(i.getTickerSymbol(), i.getQuantity());
      }
      model.finishBuild();
      view.displayMessage(String.format("Succesfully created %s", portfolioName));
    } catch (Exception e) {
      view.displayMessage(e.getMessage()
              + String.format(". %s was not created.", portfolioName));
    }
  }

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
