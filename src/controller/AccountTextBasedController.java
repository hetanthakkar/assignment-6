package controller;

import model.AccountModel;
import view.AccountView;

/**
 * The AccountTextBasedController class implements the AccountController interface and provides a
 * text-based interface to interact with the account model. It handles user input and updates to the
 * model, offering a simple and easy-to-use interface for managing account information in a
 * text-based environment.
 */
public class AccountTextBasedController implements AccountController {
  AccountModel model;
  AccountView view;
  String[] partOfInput;
  boolean quitProgramFlag;

  /** Constructs a new AccountTextBasedController object. */
  public AccountTextBasedController() {
    this.quitProgramFlag = false;
  }

  @Override
  public void startController(AccountModel model, AccountView view) throws Exception {
    System.out.println("started" + this.quitProgramFlag);
    this.model = model;
    this.view = view;
    // view.displayMessage("Enter command. To get list of commands, type help. ");
    // Scanner scanner = new Scanner(System.in);
    TextCommandExecutor textCommandExecutor = null;
    boolean portfolioCreated = false; // Flag to track if a portfolio has been created

    while (!this.quitProgramFlag) {
      System.out.println("kljlj" + this.quitProgramFlag);

      String command = String.valueOf(view.getCommand());
      String restOfCommand = String.valueOf(view.getRestOfCommand());

      // Check if the command and rest of command are not null
      if (command == null || restOfCommand == null) {
        // If either is null, wait for the view to have a valid value
        continue;
      }

      //      String command = scanner.next();
//      String command = String.valueOf(view.getCommand());
//      String restOfCommand = view.getRestOfCommand();
      int temp=0;
      System.out.println(command);
      System.out.println("lkasndfl");
      System.out.println(restOfCommand);
      //      String restOfCommand = scanner.nextLine().trim();
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
          textCommandExecutor =
              new CreateTextCommandExecutor(model, view, "inflexible", restOfCommand);
          break;
        case ("create-inflexible"):
          textCommandExecutor =
              new CreateTextCommandExecutor(model, view, "inflexible", restOfCommand);
          break;
        case ("create-flexible"):
          if (!portfolioCreated) {
            textCommandExecutor = new CreateTextCommandExecutor(model, view, command.substring(7), restOfCommand);
            portfolioCreated = true;
          } else {
            return;
          }
          break;
//          System.out.println("It came here");
//          textCommandExecutor =
//              new CreateTextCommandExecutor(model, view, "flexible", restOfCommand);
//
//          break;
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
//          view.displayMessage("Invalid command.");
          continue;
      }

      if (textCommandExecutor != null) {
        textCommandExecutor.executeCommand();
      }
    }
  }
}
