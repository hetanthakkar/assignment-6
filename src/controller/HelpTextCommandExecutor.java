package controller;

import model.AccountModel;
import view.AccountView;

class HelpTextCommandExecutor extends AbstractTextCommandExecutor{

  HelpTextCommandExecutor(AccountModel model, AccountView view){
    super(model, view);
  }
  @Override
  public void executeCommand() {
    view.displayMessage(this.listAllCommands());
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
