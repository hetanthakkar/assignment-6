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

  class MockModel implements AccountModel {

    public boolean setPortfolioNameFlag;
    public boolean addShareFlag;

    public boolean finishBuildFlag;

    public MockModel() {
      this.setPortfolioNameFlag = false;
      this.addShareFlag = false;
      this.finishBuildFlag = false;
    }

    @Override
    public void setPortfolioName(String name, String portfolioType) {
      this.setPortfolioNameFlag = true;
    }

    @Override
    public void addShare(String tickerSymbol, int quantity) throws Exception {
      this.addShareFlag = true;
    }

    @Override
    public void finishBuild() throws Exception {
      this.finishBuildFlag = true;
    }

    @Override
    public String getPortfolioComposition(String portfolioName) {
      return "Portfolio Composition.";
    }

    @Override
    public String getPortfolioTotalValueAtCertainDate(String portfolioName, String date)
            throws Exception {
      return "Portfolio Total Value";
    }

    @Override
    public String listPortfolios() {
      return "List of Portfolio";
    }

    @Override
    public String savePortfolio(String portfolioName) throws Exception {
      return "Saving Portfolio";
    }
  }

  class MockView implements AccountView {

    @Override
    public void displayMessage(String message) {
      System.out.println(message);
    }
  }

  @Test
  public void goCreatePortfolioCommand() {
    AccountModel model = new MockModel();
    AccountView view = new MockView();
    AccountController accountController = new AccountTextBasedController();

    InputStream sysInBackup = System.in; // backup System.in to restore it later
    ByteArrayInputStream in = new ByteArrayInputStream(
        ("create portfolio (AAPL-3)\n" + "quit\n").getBytes());
    System.setIn(in);

    accountController.startController(model, view);

    System.setIn(sysInBackup);

    assertTrue(((MockModel) model).setPortfolioNameFlag);
    assertTrue(((MockModel) model).addShareFlag);
    assertTrue(((MockModel) model).finishBuildFlag);
  }

  @Test
  public void goPortfolioTotalValue() {
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
  public void goListPortfolios() {
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
  public void goShowComposition() {
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
  public void goSave() {
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

