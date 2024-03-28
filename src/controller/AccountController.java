package controller;

import model.AccountModel;
import view.AccountView;

/**
 * The AccountControllerInterface interface defines a contract for classes that act as controllers
 * between the account model and account view interfaces. It provides methods for handling user
 * interactions, updating the model, and updating the view, facilitating communication between
 * the different components of an account management system.
 */

public interface AccountController {
  /**
   * Starts program and takes in input to control both model and view objects.
   *
   * @param model Account model used for program logic
   * @param view  Account view used to display on program message on user interface
   */
  void startController(AccountModel model, AccountView view) throws Exception;
}
