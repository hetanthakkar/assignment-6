package controller;

import model.AccountModel;
import view.AccountView;

class LoadCommandCommandExecutor extends AbstractTextCommandExecutor{
  String portName;
  LoadCommandCommandExecutor(AccountModel model, AccountView view, String portName){
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
