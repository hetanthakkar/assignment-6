package controller;

import model.AccountModel;
import view.AccountView;

class ShowTextCommandExecutor extends AbstractTextCommandExecutor {
  String portName;
  ShowTextCommandExecutor(AccountModel model, AccountView view, String portName) {

    super(model, view);

    this.portName = portName;

  }

  @Override

  public void executeCommand() {
    try {
      view.displayMessage(model.getPortfolioComposition(portName));
    } catch (Exception e) {
      view.displayMessage(e.getMessage());
    }
  }
}
