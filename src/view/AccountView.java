package view;

/**
 * The AccountView interface defines methods for displaying messages related to
 * account information.
 * It provides a contract for classes that handle the presentation of
 * account-related information
 * in various formats, such as text-based, graphical, or interactive interfaces.
 */

public interface AccountView {

  /**
   * Displays a message to the user.
   *
   * @param message The message to be displayed.
   */
  void displayMessage(String message);
}
