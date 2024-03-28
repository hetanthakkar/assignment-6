package controller;

/**
 * The TextCommandExecutor interface represents an executor for text-based commands.
 * Classes implementing this interface must provide an implementation
 * for the executeCommand() method,
 * which defines the behavior to be executed when the command is invoked.
 */
interface TextCommandExecutor {

  /**
   * Executes the text-based command.
   * Implementing classes must define the specific behavior to be executed when
   * the command is invoked.
   */
  void executeCommand();
}
