package controller;

import java.util.ArrayList;
import java.util.List;

import model.AccountModel;
import model.ParsedShares;
import view.AccountView;

class SellTextCommandExecutor extends AbstractTextCommandExecutor {
  String[] partOfInput;
  SellTextCommandExecutor(AccountModel model, AccountView view, String restOfCommand) {

    super(model,view);

    this.partOfInput = restOfCommand.split(" ");
  }

  @Override
  public void executeCommand() {
    if (partOfInput.length <= 1) {
      view.displayMessage("Cannot sell nothing.");
    }
    String portfolioName = partOfInput[0];
    try {
      List<ParsedShares> listNewShares = this.parseCreatePortfolioAndShare();
      for (ParsedShares i : listNewShares) {
        model.sellShare(portfolioName ,i.getTickerSymbol(), i.getQuantity());
        view.displayMessage(String.format("Successfully sold %d-%s in %s",
          i.getQuantity(), i.getTickerSymbol() ,portfolioName));
      }
    } catch (Exception e) {
      view.displayMessage(e.getMessage()
              + "Selling was not done.");
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
