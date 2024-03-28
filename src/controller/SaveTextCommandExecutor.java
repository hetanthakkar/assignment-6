package controller;

import model.AccountModel;
import view.AccountView;

/**
 * The SaveTextCommandExecutor class is responsible for executing
 * the text command to save a portfolio.
 * It extends the AbstractTextCommandExecutor class and implements
 * the executeCommand() method to fulfill the contract of the TextCommandExecutor interface.
 */
class SaveTextCommandExecutor extends AbstractTextCommandExecutor{

  /** The name of the portfolio to be saved. */
  private String portName;

  /**
   * Constructs a SaveTextCommandExecutor with the specified model, view, and portfolio name.
   *
   * @param model    the model representing the account
   * @param view     the view representing the account
   * @param portName the name of the portfolio to be saved
   */
  SaveTextCommandExecutor(AccountModel model, AccountView view, String portName){
    super(model, view);
    this.portName = portName;
  }

  /**
   * Executes the command to save a portfolio.
   * It saves the portfolio using the model and displays the result using the view.
   */
  @Override
  public void executeCommand() {
    try {
      view.displayMessage(model.savePortfolio(portName));
    } catch (Exception e) {
      view.displayMessage(e.getMessage());
    }
  }
}

