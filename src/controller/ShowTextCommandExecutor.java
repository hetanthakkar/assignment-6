package controller;

import model.AccountModel;
import view.AccountView;

/**
 * The ShowTextCommandExecutor class is responsible for executing the text command
 * to display the composition of a portfolio.
 * It extends the AbstractTextCommandExecutor class and implements the executeCommand()
 * method to fulfill the contract of the TextCommandExecutor interface.
 */
class ShowTextCommandExecutor extends AbstractTextCommandExecutor {

  /**
   * The name of the portfolio to be shown.
   */
  private final String portName;

  /**
   * Constructs a ShowTextCommandExecutor with the specified model, view, and portfolio name.
   *
   * @param model    the model representing the account
   * @param view     the view representing the account
   * @param portName the name of the portfolio to be shown
   */
  ShowTextCommandExecutor(AccountModel model, AccountView view, String portName) {
    super(model, view);
    this.portName = portName;
  }

  /**
   * Executes the command to display the composition of a portfolio.
   * It retrieves the portfolio composition using the model and displays it using the view.
   */
  @Override
  public void executeCommand() {
    try {
      view.displayMessage(model.getPortfolioComposition(portName));
    } catch (Exception e) {
      view.displayMessage(e.getMessage());
    }
  }
}

