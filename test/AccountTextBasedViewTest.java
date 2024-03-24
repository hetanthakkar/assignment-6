import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import view.AccountTextBasedView;
import view.AccountView;

import static org.junit.Assert.assertTrue;

/**
 * AccountTextBasedViewTest is a test class to test the AccountTextBasedView class functions.
 */
public class AccountTextBasedViewTest {

  @Test
  public void displayMessage() {
    AccountView view = new AccountTextBasedView();

    String message = "Hello!";
    PrintStream sysOutBackup = System.out;
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    view.displayMessage(message);

    System.setOut(sysOutBackup);

    String expected = out.toString();

    assertTrue(expected.contains(message));
  }
}