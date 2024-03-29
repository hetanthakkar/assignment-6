import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import controller.AccountController;
import controller.AccountTextBasedController;
import model.AccountModel;
import view.AccountView;

import static org.junit.Assert.assertTrue;

/**
 * AccountTextBasedControllerTest is a test class to test the AccountTextBasedController
 * class functions.
 */
public class AccountTextBasedControllerTest {

  @Test
  public void goCreatePortfolioCommand() throws Exception {
    MockModel model = new MockModel();
    AccountView view = new MockView();
    AccountController accountController = new AccountTextBasedController();

    InputStream sysInBackup = System.in; // backup System.in to restore it later
    ByteArrayInputStream in = new ByteArrayInputStream(
            ("create portfolio (AAPL-3)\n" + "quit\n").getBytes());
    System.setIn(in);

    accountController.startController(model, view);

    System.setIn(sysInBackup);

    assertTrue(model.setPortfolioNameFlag);
    assertTrue(model.addShareFlag);
    assertTrue(model.finishBuildFlag);
  }

  @Test
  public void goPortfolioTotalValue() throws Exception {
    AccountModel model = new MockModel();
    AccountView view = new MockView();
    AccountController accountController = new AccountTextBasedController();

    InputStream sysInBackup = System.in;
    ByteArrayInputStream in = new ByteArrayInputStream(("getvalue portfolio 2024-03-14\n"
            + "quit\n").getBytes());
    System.setIn(in);

    PrintStream sysOutBackup = System.out;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    accountController.startController(model, view);

    System.setIn(sysInBackup);
    System.setOut(sysOutBackup);

    String output = out.toString();

    assertTrue(output.contains("Portfolio Total Value"));
  }

  @Test
  public void goListPortfolios() throws Exception {
    AccountModel model = new MockModel();
    AccountView view = new MockView();
    AccountController accountController = new AccountTextBasedController();

    InputStream sysInBackup = System.in;
    ByteArrayInputStream in = new ByteArrayInputStream(("list\n" + "quit\n").getBytes());
    System.setIn(in);

    PrintStream sysOutBackup = System.out;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    accountController.startController(model, view);

    System.setIn(sysInBackup);
    System.setOut(sysOutBackup);

    String output = out.toString();

    assertTrue(output.contains("List of Portfolio"));
  }

  @Test
  public void goShowComposition() throws Exception {
    AccountModel model = new MockModel();
    AccountView view = new MockView();
    AccountController accountController = new AccountTextBasedController();

    InputStream sysInBackup = System.in;
    ByteArrayInputStream in = new ByteArrayInputStream(("show port\n" + "quit\n").getBytes());
    System.setIn(in);

    PrintStream sysOutBackup = System.out;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    accountController.startController(model, view);

    System.setIn(sysInBackup);
    System.setOut(sysOutBackup);

    String output = out.toString();

    assertTrue(output.contains("Portfolio Composition."));
  }

  @Test
  public void goSave() throws Exception {
    AccountModel model = new MockModel();
    AccountView view = new MockView();
    AccountController accountController = new AccountTextBasedController();

    InputStream sysInBackup = System.in;
    ByteArrayInputStream in = new ByteArrayInputStream(("save portfolio\n" + "quit\n").getBytes());
    System.setIn(in);

    PrintStream sysOutBackup = System.out;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    accountController.startController(model, view);

    System.setIn(sysInBackup);
    System.setOut(sysOutBackup);

    String output = out.toString();

    assertTrue(output.contains("Saving Portfolio"));
  }

}

