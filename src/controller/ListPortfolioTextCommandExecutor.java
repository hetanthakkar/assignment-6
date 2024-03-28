package controller;

import model.AccountModel;
import view.AccountView;

/**
 * The ListPortfolioTextCommandExecutor class is responsible
 * for executing the text command to list portfolios.
 * It extends the AbstractTextCommandExecutor class and implements
 * the executeCommand() method to fulfill the contract of the TextCommandExecutor interface.
 */
class ListPortfolioTextCommandExecutor extends AbstractTextCommandExecutor {

  /**
   * Constructs a ListPortfolioTextCommandExecutor with the specified model and view.
   *
   * @param model the model representing the account
   * @param view  the view representing the account
   */
  ListPortfolioTextCommandExecutor(AccountModel model, AccountView view) {
    super(model, view);
  }

  /**
   * Executes the command to list portfolios.
   * It retrieves the list of portfolios from the model and displays them using the view.
   */
  @Override
  public void executeCommand() {
    view.displayMessage(model.listPortfolios());
  }
}
