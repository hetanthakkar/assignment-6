package controller;

import model.AccountModel;
import view.AccountView;

/**
 * The HelpTextCommandExecutor class is responsible for executing
 * the text command to display available commands and their descriptions.
 * It extends the AbstractTextCommandExecutor class and implements
 * the executeCommand() method to fulfill the contract of the TextCommandExecutor interface.
 */
class HelpTextCommandExecutor extends AbstractTextCommandExecutor {

  /**
   * Constructs a HelpTextCommandExecutor with the specified model and view.
   *
   * @param model the model representing the account
   * @param view  the view representing the account
   */
  HelpTextCommandExecutor(AccountModel model, AccountView view) {
    super(model, view);
  }

  /**
   * Executes the command to display available commands and their descriptions.
   * It retrieves the list of commands and their descriptions using the listAllCommands() method,
   * then displays them using the view.
   */
  @Override
  public void executeCommand() {
    view.displayMessage(this.listAllCommands());
  }

  /**
   * Generates a string containing all available commands and their descriptions.
   *
   * @return a string containing all available commands and their descriptions
   */
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

  /**
   * An enum representing different commands along with their descriptions and examples.
   */
  private enum Command {

    BUY(new String[]{"buy",
            "Buy specified share on specified date. Only for flexible portfolios.",
            "buy [Portfolio Name] [date] ([TickerSymbol]-[quantity]) ([TickerSymbol]-[quantity])..."}),
    SELL(new String[]{"sell",
            "Sell specified share on specified date. Only for flexible portfolios.",
            "sell [Portfolio Name] [date] ([TickerSymbol]-[quantity]) ([TickerSymbol]-[quantity])..."}),
    CREATE(new String[]{"create",
        "Creating a new inflexible portfolio with shares in it.",
        "create [Portfolio Name] ([TickerSymbol]-[quantity]) ([TickerSymbol]-[quantity])..."}),
    CREATEINFLEXIBLE(new String[]{"create-inflexible",
            "Creating a new inflexible portfolio with shares in it.",
            "create-inflexible [Portfolio Name] ([TickerSymbol]-[quantity]) ([TickerSymbol]-[quantity])..."}),
    CREATEFLEXIBLE(new String[]{"create-flexible",
            "Creating a new flexible portfolio with shares in it.",
            "create-flexible [Portfolio Name] ([TickerSymbol]-[quantity]) ([TickerSymbol]-[quantity])..."}),
    LIST(new String[]{"list", "Listing all portfolios.", ""}),
    SHOW(new String[]{"show", "Show composition of portfolio.", "show [Portfolio Name]"}),
    GETCOST(new String[]{"get-cost", "Get cost basis of portfolio on certain date.",
            "get-cost [Portfolio Name] [yyyy-mm-dd]"}),
    GETVALUE(new String[]{"getvalue", "Get total value of portfolio on certain date.",
        "getvalue [Portfolio Name] [yyyy-mm-dd]"}),
    HELP(new String[]{"help", "Listing all available commands and their examples.", ""}),
    QUIT(new String[]{"quit", "Quit the program.", ""}),
    LOAD(new String[]{"load",
        "Load your csv file for creating portfolio. Should have first line as portfolio name",
        "load /Users/hetanthakkar/Assignment5/res/retirement/"}),
    SAVE(new String[]{"save", "Save your portfolio in a csv file", "save [portfolioName]"});

    private final String[] commandDescription;

    Command(String[] commandDescription) {
      this.commandDescription = commandDescription;
    }

    /**
     * Gets the name of the command.
     *
     * @return the name of the command
     */
    String getCommandName() {
      return this.commandDescription[0];
    }

    /**
     * Gets the description of the command.
     *
     * @return the description of the command
     */
    String getCommandDescription() {
      return this.commandDescription[1];
    }

    /**
     * Gets the example of the command.
     *
     * @return the example of the command
     */
    String getCommandExample() {
      return this.commandDescription[2];
    }
  }
}

