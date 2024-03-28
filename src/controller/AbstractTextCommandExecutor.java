package controller;

import model.AccountModel;
import view.AccountView;

abstract class AbstractTextCommandExecutor implements TextCommandExecutor {
  AccountModel model;
  AccountView view;

  AbstractTextCommandExecutor(AccountModel model, AccountView view) {
    this.model = model;
    this.view = view;
  }
}
