package controller;

import model.AccountModel;
import view.AccountView;

/**
 * AbstractTextCommandExecutor is an abstract class that
 * implements the TextCommandExecutor interface.
 * It provides basic functionality for executing text-based commands related to an account.
 */
public abstract class AbstractTextCommandExecutor implements TextCommandExecutor {

  /** The model representing the account. */
  protected AccountModel model;

  /** The view representing the account. */
  protected AccountView view;

  /**
   * Constructs an AbstractTextCommandExecutor with the specified model and view.
   *
   * @param model the model representing the account
   * @param view the view representing the account
   */
  public AbstractTextCommandExecutor(AccountModel model, AccountView view){
    this.model = model;
    this.view = view;
  }

  /**
   * Executes a text-based command.
   * Subclasses must implement this method to define the behavior of the command execution.
   *
   */
  public abstract void executeCommand();
}

