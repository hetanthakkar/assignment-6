package controller;

import model.AccountModel;
import view.AccountView;

class SaveTextCommandExecutor extends AbstractTextCommandExecutor {
  String portName;
  SaveTextCommandExecutor(AccountModel model, AccountView view, String portName) {

    super(model, view);

    this.portName = portName;
  }

  @Override

  public void executeCommand() {

    try {
      view.displayMessage(model.savePortfolio(portName));
    } catch (Exception e) {
      view.displayMessage(e.getMessage());
    }
  }
}
