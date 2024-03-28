package controller;

import java.util.List;
import java.util.Scanner;

import model.AccountModel;
import model.CsvFileIO;
import model.CsvFileIOInterface;
import model.CsvProcessor;
import model.CsvProcessorInterface;
import model.ParsedShares;
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
  public void startController(AccountModel model, AccountView view) {
    this.model = model;
    this.view = view;
    view.displayMessage("Enter command. To get list of commands, type help. ");
    Scanner scanner = new Scanner(System.in);
    while (!this.quitProgramFlag) {
      TextCommandExecutor textCommandExecutor;
      String command = scanner.next();
      String restOfCommand = scanner.nextLine().trim();
      switch (command.toLowerCase()) {
        case ("quit"):
          this.quitProgramFlag = true;
          break;
        case ("help"):
          textCommandExecutor = new HelpTextCommandExecutor(model, view);
          textCommandExecutor.executeCommand();
          break;
        case ("list"):
          textCommandExecutor = new ListPortfolioTextCommandExecutor(model, view);
          textCommandExecutor.executeCommand();
          break;
        case ("create"):
          textCommandExecutor = new CreateTextCommandExecutor(model, view, "inflexible",
                  restOfCommand);
          textCommandExecutor.executeCommand();
          break;
        case ("create-inflexible"):
          textCommandExecutor = new CreateTextCommandExecutor(model, view, "inflexible",
                  restOfCommand);
          textCommandExecutor.executeCommand();
          break;
        case ("create-flexible"):
          textCommandExecutor = new CreateTextCommandExecutor(model, view, "flexible",
                  restOfCommand);
          textCommandExecutor.executeCommand();
          break;
        case ("buy"):
          textCommandExecutor = new BuyTextCommandExecutor(model, view, restOfCommand);
          textCommandExecutor.executeCommand();
          break;
        case ("sell"):
          textCommandExecutor = new SellTextCommandExecutor(model, view, restOfCommand);
          textCommandExecutor.executeCommand();
          break;
        case ("get-cost"):
          textCommandExecutor = new GetCostTextCommandExecutor(model, view, restOfCommand);
          textCommandExecutor.executeCommand();
          break;
        case ("show"):
          textCommandExecutor = new ShowTextCommandExecutor(model, view, restOfCommand);
          textCommandExecutor.executeCommand();
          break;
        case ("getvalue"):
          textCommandExecutor = new GetValueTextCommandExecutor(model, view, restOfCommand);
          textCommandExecutor.executeCommand();
          break;
        case ("save"):
          textCommandExecutor = new SaveTextCommandExecutor(model, view, restOfCommand);
          textCommandExecutor.executeCommand();
          break;
        case ("load"):
          partOfInput = restOfCommand.split(" ");
          int lastIndex = partOfInput[1].lastIndexOf("/");
          String fileLocation = partOfInput[1].substring(0, lastIndex);
          String fileName = partOfInput[1].substring(lastIndex + 1);
          CsvFileIOInterface csvFileIO = new CsvFileIO(fileName, fileLocation);
          CsvProcessorInterface csvProcessor = new CsvProcessor(csvFileIO.getData());
          String portfolioName1 = csvProcessor.getPortfolioNameFromCsv();
          System.out.println(portfolioName1);
          model.setPortfolioName(portfolioName1, "flexible");
          try {
            List<model.ParsedShares> listNewShares = csvProcessor.getSharesFromCsv();
            for (model.ParsedShares i : listNewShares) {
              model.addShare(i.getTickerSymbol(), i.getQuantity());
              System.out.println(i.getTickerSymbol() + "As" + i.getQuantity());
            }
            model.finishBuild();
          } catch (Exception e) {
            view.displayMessage(e.getMessage()
                    + String.format(". %s was not  created.", portfolioName1));
            break;
          }
          view.displayMessage(String.format("Succesfully created %s", portfolioName1));
          break;
        default:
          view.displayMessage("Invalid command.");
          break;
      }
    }
  }
}
