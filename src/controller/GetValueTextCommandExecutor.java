package controller;

import model.AccountModel;
import view.AccountView;

class GetValueTextCommandExecutor extends AbstractTextCommandExecutor {
  String date;
  String portName;

  GetValueTextCommandExecutor(AccountModel model, AccountView view, String restOfCommand) {
    super(model, view);
    String[] parts = restOfCommand.split("\\s+");
    if (parts.length == 2) {
      portName = parts[0];
      date = parts[1];
    } else if (parts.length == 1) {
      portName = parts[0];
      date = "";
    } else {
      portName = "";
      date = "";
    }

  }

  @Override
  public void executeCommand() {
    try {
      view.displayMessage(model.getPortfolioTotalValueAtCertainDate(portName, date));
    } catch (Exception e) {
      view.displayMessage(e.getMessage());
    }
  }
}
