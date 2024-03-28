package controller;

import model.AccountModel;
import view.AccountView;

/**
 * The GetValueTextCommandExecutor class is responsible for executing the text command
 * to get the total value of a portfolio.
 * It extends the AbstractTextCommandExecutor class and implements the executeCommand()
 * method to fulfill the contract of the TextCommandExecutor interface.
 */
class GetValueTextCommandExecutor extends AbstractTextCommandExecutor {

  /** The date for which the total value is requested. */
  private String date;

  /** The name of the portfolio for which the total value is requested. */
  private String portName;

  /**
   * Constructs a GetValueTextCommandExecutor with the specified model, view,
   * and rest of the command.
   *
   * @param model         the model representing the account
   * @param view          the view representing the account
   * @param restOfCommand the remaining part of the command containing portfolio name and date
   */
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

  /**
   * Executes the command to get the total value of a portfolio.
   * It retrieves the total value using the model and displays it using the view.
   */
  @Override
  public void executeCommand() {
    try {
      view.displayMessage(model.getPortfolioTotalValueAtCertainDate(portName, date));
    } catch (Exception e) {
      view.displayMessage(e.getMessage());
    }
  }
}

