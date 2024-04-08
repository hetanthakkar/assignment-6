import controller.AccountController;
import controller.AccountTextBasedController;
import model.Account;
import model.AccountModel;
import view.AccountTextBasedView;
import view.AccountView;
import view.CreatePortfolioView;

/**
 * The Main class is the entry point for the Financial Portfolio Management
 * System.
 * It initializes the model, view, and controller, and starts the application.
 */
public class Main {
  /**
   * The main method initializes the model, view, and controller, and starts the
   * application.
   *
   * @param args The command line arguments (not used).
   */
  public static void main(String[] args) throws Exception {
    AccountModel model = new Account();
    AccountView view = new CreatePortfolioView();
    AccountController controller = new AccountTextBasedController();
    controller.startController(model, view);
  }
}
