package view;

/**
 * The AccountTextBasedView class implements the AccountView interface
 * and provides a text-based user interface for displaying messages related to
 * the account,
 * offering a simple and easy-to-understand interface for interacting with
 * account information.
 */

public class AccountTextBasedView implements AccountView {

  @Override
  public void displayMessage(String message) {
    System.out.println(message);
  }

}
