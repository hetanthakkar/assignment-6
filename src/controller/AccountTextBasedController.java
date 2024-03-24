package controller;

import java.util.ArrayList;
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
      String command = scanner.nextLine();
      partOfInput = command.split(" ");
      switch (partOfInput[0].toLowerCase()) {
        case ("quit"):
          this.quitProgramFlag = true;
          break;
        case ("help"):
          view.displayMessage(this.listAllCommands());
          break;
        case ("list"):
          String list = model.listPortfolios();
          view.displayMessage(list);
          break;
        case ("create"):
          if (partOfInput.length <= 2) {
            view.displayMessage("Cannot create empty portfolio.");
            break;
          }
          String portfolioName = partOfInput[1];
          try {
            List<ParsedShares> listNewShares = this.parseCreatePortfolioAndShare();
            model.setPortfolioName(portfolioName);
            for (ParsedShares i : listNewShares) {
              model.addShare(i.getTickerSymbol(), i.getQuantity());
            }
            model.finishBuild();
          } catch (Exception e) {
            view.displayMessage(e.getMessage()
                    + String.format(". %s was not created.", portfolioName));
            break;
          }
          view.displayMessage(String.format("Succesfully created %s", portfolioName));
          break;
        case ("load"):
          int lastIndex = partOfInput[1].lastIndexOf("/");
          String fileLocation = partOfInput[1].substring(0, lastIndex);
          String fileName = partOfInput[1].substring(lastIndex + 1);
          CsvFileIOInterface csvFileIO = new CsvFileIO(fileName, fileLocation);
          CsvProcessorInterface csvProcessor = new CsvProcessor(csvFileIO.getData());
          String portfolioName1 = csvProcessor.getPortfolioNameFromCsv();
          System.out.println(portfolioName1);
          model.setPortfolioName(portfolioName1);
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
        case ("show"):
          if (partOfInput.length != 2) {
            view.displayMessage("Please use show correctly.");
            break;
          }
          try {
            view.displayMessage(model.getPortfolioComposition(partOfInput[1]));
          } catch (Exception e) {
            view.displayMessage(e.getMessage());
          }
          break;
        case ("getvalue"):
          if (partOfInput.length != 3) {
            view.displayMessage("Please type command correctly.");
            break;
          }
          String portfolio = partOfInput[1];
          String date = partOfInput[2];
          try {
            view.displayMessage(model.getPortfolioTotalValueAtCertainDate(portfolio, date));
          } catch (Exception e) {
            view.displayMessage(e.getMessage());
          }
          break;
        case ("save"):
          if (partOfInput.length > 2) {
            view.displayMessage("Please use show correctly.");
            break;
          }
          try {
            view.displayMessage(model.savePortfolio(partOfInput[1]));
          } catch (Exception e) {
            view.displayMessage(e.getMessage());
          }
          break;
        default:
          view.displayMessage("Invalid command.");
          break;
      }
    }
  }

  private String listAllCommands() {
    StringBuilder listOfCommands = new StringBuilder("Available commands: ");
    listOfCommands.append("\n");
    for (Command i : Command.values()) {
      listOfCommands.append(i.getCommandName());
      listOfCommands.append("\n");
      listOfCommands.append("    ");
      listOfCommands.append(i.getCommandDescription());
      listOfCommands.append("\n");
      if (!i.getCommandExample().isEmpty()) {
        listOfCommands.append("    ");
        listOfCommands.append(i.getCommandExample());
        listOfCommands.append("\n");
      }
    }
    return listOfCommands.toString();
  }

  private List<ParsedShares> parseCreatePortfolioAndShare() {
    List<ParsedShares> shares = new ArrayList<ParsedShares>();
    for (int i = 2; i < this.partOfInput.length; i++) {
      String shareInput = partOfInput[i];
      if (shareInput.startsWith("(") && shareInput.endsWith(")")) {
        String[] shareInfo = shareInput.substring(1, shareInput.length() - 1).split("-");
        int quantity;
        try {
          quantity = Integer.parseInt(shareInfo[1]);
        } catch (Exception e) {
          throw new IllegalArgumentException(
                  "Quantity must be non-fractional "
                          + "and after Ticker Symbol. i.e: ([TickerSymbol]-[quantity])");
        }
        ParsedShares newShare = new ParsedShares(shareInfo[0], quantity);
        shares.add(newShare);
      } else {
        throw new IllegalArgumentException(
                "Please input shares correctly (no spaces). i.e: ([TickerSymbol]-[quantity])");
      }
    }
    return shares;
  }

  private enum Command {
    CREATE(new String[]{"create",
      "Creating a new portfolio with shares in it.",
      "create [Portfolio Name] ([TickerSymbol]-[quantity]) ([TickerSymbol]-[quantity])..."}),
    LIST(new String[]{"list", "Listing all portfolios.", ""}),
    SHOW(new String[]{"show", "Show composition of portfolio.", "show [Portfolio Name]"}),
    GETVALUE(new String[]{"getvalue", "Get total value of portfolio on certain date.",
      "getvalue [Portfolio Name] [yyyy-mm-dd]"}),
    HELP(new String[]{"help", "Listing all available commands and their examples.", ""}),
    QUIT(new String[]{"quit", "Quit the program.", ""}),
    LOAD(new String[]{"load",
      "Load your csv file for creating portfolio. Should have first line as portfolio name",
      "load [/Users/hetanthakkar/Assignment4/retirement/]"}),
    SAVE(new String[]{"save", "Save your portfolio in a csv file", "save [portfolioName]"});

    private final String[] commandDescription;

    Command(String[] commandDescription) {
      this.commandDescription = commandDescription;
    }

    String getCommandName() {
      return this.commandDescription[0];
    }

    String getCommandDescription() {
      return this.commandDescription[1];
    }

    String getCommandExample() {
      return this.commandDescription[2];
    }

  }
}
