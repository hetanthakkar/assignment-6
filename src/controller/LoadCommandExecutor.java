package controller;

import model.AccountModel;
import view.AccountView;

class LoadCommandExecutor extends AbstractTextCommandExecutor {
  String portName;

  LoadCommandExecutor(AccountModel model, AccountView view, String portName) {
    super(model, view);
    this.portName = portName;
  }

  @Override
  public void executeCommand() {
    try {
      view.displayMessage(model.loadModel(portName));
    } catch (Exception e) {
      view.displayMessage(e.getMessage());
    }
  }
}
