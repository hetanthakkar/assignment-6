package controller;

import java.util.Scanner;

import model.AccountModel;

import view.AccountView;

/**
 * The AccountTextBasedController class implements the AccountController
 * interface
 * and provides a text-based interface to interact with the account model. It
 * handles
 * user input and updates to the model, offering a simple and easy-to-use
 * interface
 * for managing account information in a text-based environment.
 */

public class AccountTextBasedController implements AccountController {
  AccountModel model;
  AccountView view;
  String[] partOfInput;
  boolean quitProgramFlag;

  /**
   * Constructs a new AccountTextBasedController object.
   */
  public AccountTextBasedController() {
    this.quitProgramFlag = false;
  }

  @Override
  public void startController(AccountModel model, AccountView view) throws Exception {
    this.model = model;
    this.view = view;
    view.displayMessage("Enter command. To get list of commands, type help. ");
    Scanner scanner = new Scanner(System.in);
    TextCommandExecutor textCommandExecutor = null;
    while (!this.quitProgramFlag) {
      String command = scanner.next();
      String restOfCommand = scanner.nextLine().trim();
      switch (command.toLowerCase()) {
        case ("quit"):
          this.quitProgramFlag = true;
          break;
        case ("help"):
          textCommandExecutor = new HelpTextCommandExecutor(model, view);
          break;
        case ("list"):
          textCommandExecutor = new ListPortfolioTextCommandExecutor(model, view);
          break;
        case ("create"):
          textCommandExecutor = new CreateTextCommandExecutor(model, view, "inflexible",
            restOfCommand);
          break;
        case ("create-inflexible"):
          textCommandExecutor = new CreateTextCommandExecutor(model, view, "inflexible",
            restOfCommand);
          break;
        case ("create-flexible"):
          textCommandExecutor = new CreateTextCommandExecutor(model, view, "flexible",
            restOfCommand);
          break;
        case ("buy"):
          textCommandExecutor = new BuyTextCommandExecutor(model, view, restOfCommand);
          break;
        case ("sell"):
          textCommandExecutor = new SellTextCommandExecutor(model, view, restOfCommand);
          break;
        case ("get-cost"):
          textCommandExecutor = new GetCostTextCommandExecutor(model, view, restOfCommand);
          break;
        case ("show"):
          textCommandExecutor = new ShowTextCommandExecutor(model, view, restOfCommand);
          break;
        case ("getvalue"):
          textCommandExecutor = new GetValueTextCommandExecutor(model, view, restOfCommand);
          break;
        case ("save"):
          textCommandExecutor = new SaveTextCommandExecutor(model, view, restOfCommand);
          break;
        case ("load"):
          textCommandExecutor = new LoadCommandExecutor(model, view, restOfCommand);
          break;
        default:
          view.displayMessage("Invalid command.");
          continue;
      }

      if (textCommandExecutor != null) {
        textCommandExecutor.executeCommand();
      }
    }
  }

}
