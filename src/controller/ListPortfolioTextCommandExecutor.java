package controller;

import model.AccountModel;
import view.AccountView;

class ListPortfolioTextCommandExecutor extends AbstractTextCommandExecutor {

  ListPortfolioTextCommandExecutor(AccountModel model, AccountView view){
    super(model, view);
  }
  @Override
  public void executeCommand() {
    view.displayMessage(model.listPortfolios());
  }
}
